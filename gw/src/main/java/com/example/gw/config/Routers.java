package com.example.gw.config;

import com.example.gw.filter.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Routers {

    private final AuthFilter authFilter;

    @Value("${auth-ms.url}")
    private String authMsUrl;

    @Value("${user-ms.url}")
    private String userMsUrl;

    @Value("${card-ms.url}")
    private String cardMsUrl;

    @Value("${transfer-ms.url}")
    private String transferMsUrl;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, GatewayFilter rateLimiterFilter) {
        return builder.routes()
                .route("auth-ms", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(rateLimiterFilter))
                        .uri(authMsUrl))

                .route("user-ms", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(authFilter)
                                .filter(rateLimiterFilter))
                        .uri(userMsUrl))

                .route("card-ms", r -> r.path("/api/cards/**")
                        .filters(f -> f.filter(authFilter)
                                .filter(rateLimiterFilter))
                        .uri(cardMsUrl))

                .route("transfer-ms", r -> r.path("/api/transfer/**")
                        .filters(f -> f.filter(authFilter)
                                .filter(rateLimiterFilter))
                        .uri(transferMsUrl))

                .build();
    }
}
