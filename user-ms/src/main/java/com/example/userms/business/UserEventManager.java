package com.example.userms.business;

import static com.example.userms.config.rabbit.QueueConstants.EXCHANGE_USERS;
import static com.example.userms.config.rabbit.QueueConstants.KEY_USERS;

import com.example.userms.business.model.UserEvent;
import com.example.userms.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventManager {

    private final AmqpTemplate amqpTemplate;

    public void userCreated(User user) {
        var userEvent = UserEvent.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();

        amqpTemplate.convertAndSend(EXCHANGE_USERS, KEY_USERS, userEvent);
    }
}
