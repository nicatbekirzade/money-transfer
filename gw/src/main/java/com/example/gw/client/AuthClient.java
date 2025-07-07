package com.example.gw.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "AuthClient", url = "${auth-ms.url}", path = "/api/auth/v1")
public interface AuthClient {

    @GetMapping(value = "/check-auth")
    boolean isAuthorized(
            @RequestParam String token,
            @RequestParam String endpoint,
            @RequestParam String httpMethod);
}
