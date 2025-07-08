package com.example.transferms.business;


import static com.example.transferms.config.rabbit.QueueConstants.EXCHANGE_TRANSACTION;
import static com.example.transferms.config.rabbit.QueueConstants.KEY_TRANSFER;

import com.example.transferms.business.model.TransferInitiateEvent;
import com.example.transferms.entity.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferEventManager {

    private final AmqpTemplate amqpTemplate;

    public void initiateTransfer(Transfer t) {
        var event = TransferInitiateEvent.builder()
                .id(t.getId())
                .fromCard(t.getFromCard())
                .toCard(t.getToCard())
                .amount(t.getAmount())
                .transactionNumber(t.getTransactionNumber())
                .build();
        amqpTemplate.convertAndSend(EXCHANGE_TRANSACTION, KEY_TRANSFER, event);
    }
}
