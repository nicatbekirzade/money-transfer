package com.example.cardms.api.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {

    private UUID id;
    private String cardNumber;
    private BigDecimal balance;
    private Boolean active;
}
