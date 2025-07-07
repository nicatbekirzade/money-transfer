package com.example.transferms.config.rabbit;

import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConstants {

    //QUEUE NAME
    public static final String QUEUE_USERS = "q.users";
    public static final String QUEUE_TOP_UP = "q.top_up";
    public static final String QUEUE_TRANSFER = "q.transfer";
    public static final String QUEUE_TRANSFER_RESPONSE = "q.transfer_response";
    public static final String QUEUE_TRANSACTION_LOG = "q.transaction_log";


    public static final String DLQ_USERS = "q.user_dlq";

    //BINDING KEY
    public static final String KEY_USERS = "users-key";

    public static final String KEY_TRANSFER = "transfer-response.transaction";
    public static final String KEY_TOP_UP = "top-up.transaction";
    public static final String KEY_TRANSFER_RESPONSE = "transfer-response.transaction";
    public static final String KEY_TRANSACTION_LOG = "*.transaction";

    //EXCHANGE
    public static final String EXCHANGE_USERS = "x.users";
    public static final String EXCHANGE_CARDS = "x.cards";
    public static final String EXCHANGE_TRANSACTION = "x.transactions";
}