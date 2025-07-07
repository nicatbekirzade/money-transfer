package com.example.authms.client;

import com.example.authms.api.model.User;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "UserClient",
        url = "${user-ms.url}",
        path = "/api/users/v1/user")
public interface UserClient {

    @GetMapping(consumes = "application/json")
    Optional<User> getUserByEmail(@RequestParam String email);

}