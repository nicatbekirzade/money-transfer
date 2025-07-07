package com.example.transferms.config.rabbit;

import static com.example.transferms.config.rabbit.QueueConstants.KEY_TOP_UP;
import static com.example.transferms.config.rabbit.QueueConstants.KEY_TRANSACTION_LOG;
import static com.example.transferms.config.rabbit.QueueConstants.KEY_TRANSFER;
import static com.example.transferms.config.rabbit.QueueConstants.KEY_TRANSFER_RESPONSE;
import static com.example.transferms.config.rabbit.QueueConstants.KEY_USERS;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Bindings {

    @Bean
    public Binding transferResponseQueueBinding(Queue transferResponseQueue, TopicExchange transactionExchange) {
        return BindingBuilder
                .bind(transferResponseQueue)
                .to(transactionExchange)
                .with(KEY_TRANSFER_RESPONSE);
    }

    @Bean
    public Binding transferQueueBinding(Queue transferQueue, TopicExchange transactionExchange) {
        return BindingBuilder
                .bind(transferQueue)
                .to(transactionExchange)
                .with(KEY_TRANSFER);
    }

    @Bean
    public Binding topUpTransactionBinding(Queue topUpQueue, TopicExchange transactionExchange) {
        return BindingBuilder
                .bind(topUpQueue)
                .to(transactionExchange)
                .with(KEY_TOP_UP);
    }

    @Bean
    public Binding transactionLogQueueBinding(Queue transactionLogQueue, TopicExchange transactionExchange) {
        return BindingBuilder
                .bind(transactionLogQueue)
                .to(transactionExchange)
                .with(KEY_TRANSACTION_LOG);
    }


    //user
    @Bean
    public Binding usersBindings(Queue usersQueue, DirectExchange directUserExchange) {
        return BindingBuilder
                .bind(usersQueue)
                .to(directUserExchange)
                .with(KEY_USERS);
    }

}
