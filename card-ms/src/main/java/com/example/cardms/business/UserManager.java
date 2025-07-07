package com.example.cardms.business;

import com.example.cardms.exception.CustomValidationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final HttpServletRequest httpServletRequest;

    public UUID getXUserId() {
        String userId = httpServletRequest.getHeader("X-User-ID");
        if (userId == null || userId.isEmpty()) throw new CustomValidationException("X-User-ID is missing");

        try {
            return UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new CustomValidationException("X-User-ID is invalid");
        }
    }
}
