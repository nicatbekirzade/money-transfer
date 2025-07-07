package com.example.transferms.business.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferInitiateEvent {

    private UUID id;
    private String fromCard;
    private String toCard;
    private BigDecimal amount;
}
