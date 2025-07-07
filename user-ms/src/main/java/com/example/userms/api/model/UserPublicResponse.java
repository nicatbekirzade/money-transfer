package com.example.userms.api.model;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPublicResponse {

    UUID id;
    String email;
    String firstName;
    String lastName;
    String fatherName;
    String jobTitle;
    Boolean active;
}
