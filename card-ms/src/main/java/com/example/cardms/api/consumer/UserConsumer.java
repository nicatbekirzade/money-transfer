package com.example.cardms.api.consumer;

import static com.example.cardms.config.rabbit.QueueConstants.QUEUE_USERS;

import com.example.cardms.business.CardManager;
import com.example.cardms.business.model.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConsumer {

    private final CardManager cardManager;

    @RabbitListener(queues = {QUEUE_USERS})
    public void userListener(UserEvent event) {
        cardManager.createForNewUser(event);
    }

}
