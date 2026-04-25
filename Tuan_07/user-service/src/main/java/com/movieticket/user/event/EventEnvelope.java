package com.movieticket.user.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.UUID;

/**
 * Envelope chung cho mọi event publish qua RabbitMQ.
 * Schema được khai báo trong ai-agent/context/api.md mục 3.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventEnvelope<T>(
        String eventId,
        String eventType,
        Instant occurredAt,
        String producer,
        T payload
) {
    public static <T> EventEnvelope<T> of(String eventType, String producer, T payload) {
        return new EventEnvelope<>(
                UUID.randomUUID().toString(),
                eventType,
                Instant.now(),
                producer,
                payload
        );
    }
}
