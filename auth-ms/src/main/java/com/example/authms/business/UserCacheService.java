package com.example.authms.business;

import com.example.authms.api.model.User;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCacheService {

    private final RedisTemplate<String, User> userRedisTemplate;
    private static final String USER_KEY_PREFIX = "user:";

    public User getUserFromCache(String username) {
        String key = USER_KEY_PREFIX + username;
        return userRedisTemplate.opsForValue().get(key);
    }

    public void cacheUser(String username, User userDto) {
        String key = USER_KEY_PREFIX + username;
        userRedisTemplate.opsForValue().set(key, userDto, Duration.ofMinutes(60));
    }
}