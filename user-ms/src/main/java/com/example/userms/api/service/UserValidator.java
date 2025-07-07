package com.example.userms.api.service;

import com.example.userms.business.UserManager;
import com.example.userms.exception.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserManager userManager;

    public void validateCreateUserRequest(String email) {
        if (userManager.existsByEmail(email)) {
            throw new AlreadyExistsException((Object) email);
        }
    }
}
