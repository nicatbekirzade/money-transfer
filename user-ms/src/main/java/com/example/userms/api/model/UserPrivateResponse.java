package com.example.userms.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPrivateResponse {

    UUID id;
    String email;
    String password;
    Boolean active;
    Boolean verified;
    List<RoleResponse> roles = new ArrayList<>();
}
