package com.example.userms.api.controller;


import com.example.userms.api.model.UserInfo;
import com.example.userms.api.model.UserPrivateResponse;
import com.example.userms.api.model.UserRequest;
import com.example.userms.api.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserAccountController {

    private final UserService userService;

    @GetMapping
    public Optional<UserPrivateResponse> getUserByEmail(
            @RequestParam
            @NotEmpty(message = "{cannot_be_blank}")
            @Email(message = "{invalid_email}") String email) {
        return Optional.ofNullable(userService.getUserByEmail(email));
    }

    @GetMapping("/info")
    public UserInfo getUserInfo() {
        return userService.getUserByUserId();
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
    }
}