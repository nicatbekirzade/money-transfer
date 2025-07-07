package com.example.userms.api.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdDto {

    @NotNull
    private UUID id;
}
