package com.example.cardms.business;

import static com.example.cardms.config.rabbit.QueueConstants.EXCHANGE_CARDS;
import static com.example.cardms.config.rabbit.QueueConstants.EXCHANGE_TRANSACTION;
import static com.example.cardms.config.rabbit.QueueConstants.KEY_TOP_UP;
import static com.example.cardms.config.rabbit.QueueConstants.KEY_TRANSFER_RESPONSE;

import com.example.cardms.business.model.TransactionLogEvent;
import com.example.cardms.business.model.TransactionStatus;
import com.example.cardms.business.model.TransactionType;
import com.example.cardms.business.model.TransferInitiateEvent;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardEventManager {

    private final AmqpTemplate amqpTemplate;

    public void topUpEvent(UUID userId, BigDecimal amount, long epochMilli) {
        var event = TransactionLogEvent.builder()
                .fromUserId(userId).amount(amount).type(TransactionType.TOP_UP).timestamp(epochMilli)
                .build();
        amqpTemplate.convertAndSend(EXCHANGE_TRANSACTION, KEY_TOP_UP, event);
    }

    public void updateTransfer(TransferInitiateEvent event, UUID fromUserId, UUID toUserId, int status, String message) {
        //SEND PARALLEL TO NOTIFICATION QUE AND ETC.
        var logEvent = TransactionLogEvent.builder()
                .id(event.getId())
                .failureReason(message)
                .fromUserId(fromUserId)
                .fromCardId(formatCardNumber(event.getFromCard()))
                .toUserId(toUserId)
                .toCardId(formatCardNumber(event.getToCard()))
                .status(status == 0 ? TransactionStatus.SUCCESS : TransactionStatus.FAILED)
                .type(TransactionType.TRANSFER)
                .amount(event.getAmount())
                .timestamp(Instant.now().toEpochMilli())
                .build();
        amqpTemplate.convertAndSend(EXCHANGE_TRANSACTION, KEY_TRANSFER_RESPONSE, logEvent);
    }

    private String formatCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            throw new IllegalArgumentException("Card number must be 16 digits.");
        }
        String start = cardNumber.substring(0, 4);
        String end = cardNumber.substring(12);
        String masked = "*".repeat(8);
        return start + masked + end;
    }

}
