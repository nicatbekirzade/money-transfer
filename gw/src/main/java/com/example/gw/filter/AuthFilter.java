package com.example.gw.filter;

import com.example.gw.business.AuthService;
import com.example.gw.util.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    private final AuthService authService;
    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = extractToken(exchange.getRequest().getHeaders());
        //check token black list
        String endpoint = exchange.getRequest().getPath().toString();
        String httpMethod = exchange.getRequest().getMethod().toString();

        if (isPublicUrl(endpoint)) {
            return chain.filter(exchange);
        }

        if (token != null) {
            try {
                if (!authService.isAuthorized(token, endpoint, httpMethod)) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            } catch (FeignException ex) {
                return handleFeignException(exchange, ex);
            } catch (Exception ex) {
                return handleException(exchange, ex);
            }
            ServerHttpRequest modifiedRequest = addUserIdToRequestHeader(token, exchange);
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        }
        return chain.filter(exchange);
    }

    //skip all docs & login url
    private boolean isPublicUrl(String endpoint) {
        return endpoint.matches(".*/v3/api-docs.*")
               || endpoint.matches(".*/swagger-ui.*")
               || endpoint.matches(".*/swagger-ui.html.*")
               || endpoint.matches(".*/api/auth/v1/user/signup")
               || endpoint.matches(".*/api/auth/v1/authenticate");
    }

    private Mono<Void> handleFeignException(ServerWebExchange exchange, FeignException ex) {
        HttpStatus status = HttpStatus.resolve(ex.status());
        String body = ex.contentUTF8();

        exchange.getResponse().setStatusCode(status);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes());
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    private Mono<Void> handleException(ServerWebExchange exchange, Exception ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String path = exchange.getRequest().getPath().toString();
        Map<String, Object> errorBody = ErrorResponseBuilder.buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), 500, Collections.emptyList(), path
        );

        byte[] bytes;
        try {
            bytes = new ObjectMapper().writeValueAsBytes(errorBody);
        } catch (JsonProcessingException e) {
            bytes = ("{\"message\":\"Internal serialization error\"}").getBytes(StandardCharsets.UTF_8);
        }

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    private ServerHttpRequest addUserIdToRequestHeader(String token, ServerWebExchange exchange) {
        UUID userId = jwtService.extractUserId(token);

        return exchange.getRequest()
                .mutate()
                .header("X-User-ID", userId.toString())
                .build();
    }

    private static String extractToken(HttpHeaders headers) {
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}