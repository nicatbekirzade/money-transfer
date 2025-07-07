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
public class TransactionLogEvent {

    private UUID id;
    private Long transactionId;
    private UUID fromUserId;
    private UUID toUserId;
    private String fromCardId;
    private String toCardId;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private String failureReason;
    private Long timestamp;
}