package com.example.transactionlog.entity;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "transaction_logs")
public class TransactionLogDocument {

    @Id
    private String id; // Matches event.id

    private Long transactionId;

    private String fromUserId;
    private String toUserId;

    private String fromCardId;
    private String toCardId;

    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;

    private String failureReason;

    private Long timestamp;
}