package com.example.authms.api.service;


import com.example.authms.business.AuthCheckManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCheckService {

    private final AuthCheckManager authManager;

    public boolean isAuthorized(String token, String endpoint, String httpMethod) {
        return authManager.isAuthorized(token, endpoint, httpMethod);
    }
}
