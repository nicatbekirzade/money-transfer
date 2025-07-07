package com.example.cardms.business.model;

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
public class TransferUpdateEvent {

    private UUID id;
    private BigDecimal amount;
    private int status;
    private String failureReason;
}
