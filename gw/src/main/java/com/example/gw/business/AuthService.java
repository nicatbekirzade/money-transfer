package com.example.gw.business;

import com.example.gw.client.AuthClient;
import com.example.gw.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Lazy
    @Autowired
    private AuthClient authClient;

    private final AuthCacheService authCacheService;
    private final JwtService jwtService;

    public boolean isAuthorized(String token, String endpoint, String httpMethod) {
        String userId = jwtService.extractUserId(token).toString();

        Boolean cachedAuthorization = authCacheService.getCachedAuthorization(
                userId,
                endpoint,
                httpMethod);
        if (cachedAuthorization != null) {
            return cachedAuthorization;
        } else {
            boolean authorized = authClient.isAuthorized(token, endpoint, httpMethod);
            authCacheService.cacheAuthorization(
                    userId,
                    endpoint,
                    httpMethod,
                    authorized
            );
            return authorized;
        }
    }
}
