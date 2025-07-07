package com.example.transferms.api.consumer;


import static com.example.transferms.config.rabbit.QueueConstants.QUEUE_TRANSFER_RESPONSE;

import com.example.transferms.api.model.TransactionLogEvent;
import com.example.transferms.business.TransferManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferConsumer {

    private final TransferManager transferManager;

    @RabbitListener(queues = {QUEUE_TRANSFER_RESPONSE})
    public void transferListener(TransactionLogEvent event) {
        transferManager.updateTransferStatus(event);
    }
}
