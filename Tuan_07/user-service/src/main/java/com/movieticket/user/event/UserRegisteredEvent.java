package com.movieticket.user.event;

public record UserRegisteredEvent(
        String userId,
        String username,
        String email
) {
    public static final String TYPE = "USER_REGISTERED";
    public static final String ROUTING_KEY = "user.registered";
}
