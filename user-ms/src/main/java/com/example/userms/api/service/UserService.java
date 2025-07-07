package com.example.userms.api.service;

import com.example.userms.api.model.UserInfo;
import com.example.userms.api.model.UserRequest;
import com.example.userms.api.model.UserPrivateResponse;
import com.example.userms.business.UserManager;
import com.example.userms.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserManager manager;
    private final UserValidator validator;

    public void createUser(UserRequest userRequest) {
        validator.validateCreateUserRequest(userRequest.getEmail());
        manager.create(userRequest);
    }

    public UserPrivateResponse getUserByEmail(String email) {
        User user = manager.getUserByEmail(email);
        return user != null ? modelMapper.map(user, UserPrivateResponse.class) : null;
    }

    public UserInfo getUserByUserId() {
        return modelMapper.map(manager.getUserByXUserId(), UserInfo.class);
    }

}
