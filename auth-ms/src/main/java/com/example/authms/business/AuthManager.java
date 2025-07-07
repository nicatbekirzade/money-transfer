package com.example.authms.business;


import com.example.authms.api.model.AuthResponse;
import com.example.authms.api.model.User;
import com.example.authms.configuration.JwtService;
import com.example.authms.configuration.MyUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthManager {

    private final UserManager userManager;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(String username, String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        MyUserPrincipal user = (MyUserPrincipal) authenticate.getPrincipal();
        return jwtService.generateToken(user);
    }

    public AuthResponse refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        User user = getUserByEmail(username);
        return jwtService.generateToken(user);
    }

    private User getUserByEmail(String email) {
        return userManager.getUserByEmailAndUpdate(email);
    }
}
