package com.example.transactionlog.entity;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "transaction-logs")
public class TransactionLogIndex {

    @Id
    private UUID id;

    @Field(type = FieldType.Long)
    private Long transactionId;

    @Field(type = FieldType.Keyword)
    private UUID fromUserId;

    @Field(type = FieldType.Keyword)
    private UUID toUserId;

    @Field(type = FieldType.Keyword)
    private String fromCardId;

    @Field(type = FieldType.Keyword)
    private String toCardId;

    @Field(type = FieldType.Scaled_Float, scalingFactor = 100)
    private BigDecimal amount;

    @Field(type = FieldType.Keyword)
    private TransactionType type;

    @Field(type = FieldType.Keyword)
    private TransactionStatus status;

    @Field(type = FieldType.Text)
    private String failureReason;

    @Field(type = FieldType.Long)
    private Long timestamp;
}