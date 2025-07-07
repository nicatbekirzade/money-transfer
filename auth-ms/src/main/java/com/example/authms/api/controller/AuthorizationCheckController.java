package com.example.authms.api.controller;

import com.example.authms.api.service.AuthCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AuthorizationCheckController {

    private final AuthCheckService service;

    @GetMapping("/check-auth")
    boolean isAuthorized(@RequestParam(required = false) String token,
                         @RequestParam String endpoint,
                         @RequestParam String httpMethod) {
        return service.isAuthorized(token, endpoint, httpMethod);
    }
}
