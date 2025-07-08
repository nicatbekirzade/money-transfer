package com.example.gw.business;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCacheService {

    private final RedisTemplate<String, Boolean> apiAuthTemplate;
    private final Duration ttl = Duration.ofMinutes(1);

    public Boolean getCachedAuthorization(String userId, String method, String endpoint) {
        String key = generateKey(userId, method, endpoint);
        return apiAuthTemplate.opsForValue().get(key);
    }

    public void cacheAuthorization(String userId, String method, String endpoint, boolean authorized) {
        String key = generateKey(userId, method, endpoint);
        apiAuthTemplate.opsForValue().set(key, authorized, ttl);
    }

    private String generateKey(String userId, String method, String endpoint) {
        return String.format("auth:%s:%s:%s", userId, method.toUpperCase(), endpoint.toLowerCase());
    }
}