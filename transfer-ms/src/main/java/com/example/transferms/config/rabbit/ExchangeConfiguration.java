package com.example.transferms.config.rabbit;


import static com.example.transferms.config.rabbit.QueueConstants.EXCHANGE_CARDS;
import static com.example.transferms.config.rabbit.QueueConstants.EXCHANGE_TRANSACTION;
import static com.example.transferms.config.rabbit.QueueConstants.EXCHANGE_USERS;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfiguration {

    @Bean
    public DirectExchange directCardExchange() {
        return new DirectExchange(EXCHANGE_CARDS);
    }

    @Bean
    public TopicExchange transactionExchange() {
        return new TopicExchange(EXCHANGE_TRANSACTION);
    }

    @Bean
    public DirectExchange directUserExchange() {
        return new DirectExchange(EXCHANGE_USERS);
    }

}
