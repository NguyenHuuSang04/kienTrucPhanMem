package com.movieticket.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.movieticket.user.domain.UserAccount;
import com.movieticket.user.dto.LoginRequest;
import com.movieticket.user.dto.LoginResponse;
import com.movieticket.user.dto.RegisterRequest;
import com.movieticket.user.dto.UserResponse;
import com.movieticket.user.exception.AppException;
import com.movieticket.user.messaging.UserEventPublisher;
import com.movieticket.user.repository.UserRepository;
import com.movieticket.user.security.JwtService;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private UserEventPublisher publisher;

    @InjectMocks private UserServiceImpl service;

    @Test
    void register_shouldHashPasswordAndPublishEvent() {
        RegisterRequest req = new RegisterRequest("alice", "alice@example.com",
                "Secret@123", "Alice");
        when(userRepository.existsByUsername("alice")).thenReturn(false);
        when(userRepository.existsByEmail("alice@example.com")).thenReturn(false);
        when(passwordEncoder.encode("Secret@123")).thenReturn("hashed");
        when(userRepository.save(any(UserAccount.class))).thenAnswer(i -> i.getArgument(0));

        UserResponse resp = service.register(req);

        assertThat(resp.username()).isEqualTo("alice");
        assertThat(resp.roles()).containsExactly("USER");
        verify(passwordEncoder).encode("Secret@123");
        verify(publisher).publishUserRegistered(argThat(e ->
                e.username().equals("alice") && e.email().equals("alice@example.com")));
    }

    @Test
    void register_shouldFailWhenUsernameTaken() {
        RegisterRequest req = new RegisterRequest("alice", "alice@example.com",
                "Secret@123", "Alice");
        when(userRepository.existsByUsername("alice")).thenReturn(true);

        assertThatThrownBy(() -> service.register(req))
                .isInstanceOf(AppException.class)
                .hasMessageContaining("Username");
    }

    @Test
    void login_shouldReturnTokenWhenCredentialsValid() {
        UserAccount user = UserAccount.builder()
                .id("u-1").username("alice").email("alice@example.com")
                .passwordHash("hashed").roles("USER")
                .createdAt(Instant.now()).updatedAt(Instant.now())
                .build();
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Secret@123", "hashed")).thenReturn(true);
        when(jwtService.issue("u-1", "alice", java.util.List.of("USER"))).thenReturn("tok");
        when(jwtService.getExpiresInSeconds()).thenReturn(3600L);

        LoginResponse resp = service.login(new LoginRequest("alice", "Secret@123"));

        assertThat(resp.accessToken()).isEqualTo("tok");
        assertThat(resp.tokenType()).isEqualTo("Bearer");
        assertThat(resp.user().username()).isEqualTo("alice");
    }

    @Test
    void login_shouldFailWhenPasswordWrong() {
        UserAccount user = UserAccount.builder()
                .id("u-1").username("alice").email("alice@example.com")
                .passwordHash("hashed").roles("USER")
                .createdAt(Instant.now()).updatedAt(Instant.now())
                .build();
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);

        assertThatThrownBy(() -> service.login(new LoginRequest("alice", "wrong")))
                .isInstanceOf(AppException.class)
                .hasMessageContaining("Invalid");
    }
}
