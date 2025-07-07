package com.example.authms.api.service;

import com.example.authms.api.model.AuthResponse;
import com.example.authms.business.AuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthManager authManager;

    public AuthResponse authenticate(String username, String password) {
        return authManager.authenticate(username, password);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }
        return authManager.refreshToken(refreshToken);
    }
}
