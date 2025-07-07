package com.example.authms.api.controller;


import com.example.authms.api.model.AuthResponse;
import com.example.authms.api.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AuthController {

    private final AuthService service;

    @PostMapping("/authenticate")
    public AuthResponse login(@RequestParam String username, @RequestParam String password) {
        return service.authenticate(username, password);
    }

    @PostMapping("/token")
    public AuthResponse refreshToken(HttpServletRequest request) {
        return service.refreshToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    }
}
