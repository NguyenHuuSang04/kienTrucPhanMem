package com.movieticket.user.messaging;

import com.movieticket.user.config.RabbitMQConfig;
import com.movieticket.user.event.EventEnvelope;
import com.movieticket.user.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private static final String PRODUCER = "user-service";

    private final RabbitTemplate rabbitTemplate;

    public void publishUserRegistered(UserRegisteredEvent payload) {
        EventEnvelope<UserRegisteredEvent> envelope =
                EventEnvelope.of(UserRegisteredEvent.TYPE, PRODUCER, payload);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                UserRegisteredEvent.ROUTING_KEY,
                envelope
        );
        log.info("Published USER_REGISTERED userId={} eventId={}",
                payload.userId(), envelope.eventId());
    }
}
