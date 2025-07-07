package com.example.userms.config.rabbit;

import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConstants {

    //QUEUE NAME
    public static final String QUEUE_USERS = "q.users";

    public static final String DLQ_USERS = "q.user_dlq";

    //BINDING KEY
    public static final String KEY_USERS = "users-key";

    //EXCHANGE
    public static final String EXCHANGE_USERS = "x.users";
}