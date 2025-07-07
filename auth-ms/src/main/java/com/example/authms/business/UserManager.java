package com.example.authms.business;

import com.example.authms.client.UserClient;
import com.example.authms.exception.constants.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserClient userClient;
    private final UserCacheService userCacheService;

    public com.example.authms.api.model.User getUserByEmailAndUpdate(String email) {
        var userFromCache = userCacheService.getUserFromCache(email);
        if (userFromCache != null) {
            System.out.println("from cache"); //fixme
            return userFromCache;
        } else {
            var userFromMs = userClient.getUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(ErrorInfo.BAD_CREDENTIALS.getMessageKey()));
            userCacheService.cacheUser(email, userFromMs);
            return userFromMs;
        }
    }

}
