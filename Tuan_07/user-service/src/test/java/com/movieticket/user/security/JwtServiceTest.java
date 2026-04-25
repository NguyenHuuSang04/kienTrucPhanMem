package com.movieticket.user.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    private static final String SECRET = "test-secret-with-at-least-32-bytes-long-key-here";

    @Test
    void issue_shouldProduceParseableToken() {
        JwtService svc = new JwtService(SECRET, 3600);
        String token = svc.issue("u-1", "alice", List.of("USER"));
        assertThat(token).isNotBlank();
        assertThat(token.split("\\.")).hasSize(3);
    }

    @Test
    void constructor_shouldRejectShortSecret() {
        assertThatThrownBy(() -> new JwtService("short", 3600))
                .isInstanceOf(IllegalStateException.class);
    }
}
