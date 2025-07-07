package com.example.transferms.config.rabbit;


import static com.example.transferms.config.rabbit.QueueConstants.QUEUE_TOP_UP;
import static com.example.transferms.config.rabbit.QueueConstants.QUEUE_TRANSACTION_LOG;
import static com.example.transferms.config.rabbit.QueueConstants.QUEUE_TRANSFER;
import static com.example.transferms.config.rabbit.QueueConstants.QUEUE_TRANSFER_RESPONSE;
import static com.example.transferms.config.rabbit.QueueConstants.QUEUE_USERS;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {

    @Bean
    Queue transactionLogQueue() {
        return QueueBuilder.durable(QUEUE_TRANSACTION_LOG)
                .build();
    }

    @Bean
    Queue transferResponseQueue() {
        return QueueBuilder.durable(QUEUE_TRANSFER_RESPONSE)
                .build();
    }

    @Bean
    Queue transferQueue() {
        return QueueBuilder.durable(QUEUE_TRANSFER)
                .build();
    }

    @Bean
    Queue topUpQueue() {
        return QueueBuilder.durable(QUEUE_TOP_UP)
                .build();
    }

    //user
    @Bean
    Queue usersQueue() {
        return QueueBuilder.durable(QUEUE_USERS)
                .build();
    }
}
