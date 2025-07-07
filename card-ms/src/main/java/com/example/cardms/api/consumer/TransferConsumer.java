package com.example.cardms.api.consumer;

import static com.example.cardms.config.rabbit.QueueConstants.QUEUE_TRANSFER;

import com.example.cardms.business.CardManager;
import com.example.cardms.business.model.TransferInitiateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferConsumer {

    private final CardManager cardManager;

    @RabbitListener(queues = {QUEUE_TRANSFER})
    public void transferListener(TransferInitiateEvent event) {
        cardManager.makeTransfer(event);
    }
}
