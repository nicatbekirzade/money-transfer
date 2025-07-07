package com.example.userms.business;

import com.example.userms.api.model.UserRequest;
import com.example.userms.entity.Contact;
import com.example.userms.entity.Role;
import com.example.userms.entity.User;
import com.example.userms.exception.CustomValidationException;
import com.example.userms.exception.NotFoundException;
import com.example.userms.exception.constants.ErrorInfo;
import com.example.userms.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManager {

    private final PasswordEncoder encoder;
    private final RoleManager roleManager;
    private final UserRepository userRepository;
    private final UserEventManager userEventManager;
    private final HttpServletRequest httpServletRequest;
    private final ModelMapper modelMapper;

    public void create(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        user.setRoles(getUserDefaultRole());
        user.setContact(getContact(userRequest));
        user.setPassword(encoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        userEventManager.userCreated(user);
        //sendVerificationEmail(user);
    }

    public UUID getXUserId() {
        String userId = getXUserIdAsString();
        if (userId == null) throw new CustomValidationException("X-User-ID is missing");
        return UUID.fromString(userId);
    }

    public String getXUserIdAsString() {
        return httpServletRequest.getHeader("X-User-ID");
    }

    public User getUserByXUserId() {
        return getById(getXUserId());
    }

    public User getById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorInfo.USER_NOT_FOUND));
    }

    public User getUserCoursesByUserId() {
        return userRepository.findById(getXUserId())
                .orElseThrow(() -> new NotFoundException(ErrorInfo.USER_NOT_FOUND));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getUserByEmailOrThrow(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorInfo.USER_NOT_FOUND));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private List<Role> getUserDefaultRole() {
        return List.of(roleManager.getRoleByName("USER"));
    }

    private Contact getContact(UserRequest userRequest) {
        return Contact.builder()
                .phone(userRequest.getPhone())
                .build();
    }
}
