package com.movieticket.user.service;

import com.movieticket.user.domain.UserAccount;
import com.movieticket.user.dto.LoginRequest;
import com.movieticket.user.dto.LoginResponse;
import com.movieticket.user.dto.RegisterRequest;
import com.movieticket.user.dto.UserResponse;
import com.movieticket.user.event.UserRegisteredEvent;
import com.movieticket.user.exception.AppException;
import com.movieticket.user.messaging.UserEventPublisher;
import com.movieticket.user.repository.UserRepository;
import com.movieticket.user.security.JwtService;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserEventPublisher publisher;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new AppException(HttpStatus.CONFLICT, "USERNAME_TAKEN",
                    "Username already in use");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new AppException(HttpStatus.CONFLICT, "EMAIL_TAKEN",
                    "Email already in use");
        }

        Instant now = Instant.now();
        UserAccount user = UserAccount.builder()
                .id("u-" + UUID.randomUUID().toString().substring(0, 8))
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .roles("USER")
                .createdAt(now)
                .updatedAt(now)
                .build();

        UserAccount saved = userRepository.save(user);
        publisher.publishUserRegistered(new UserRegisteredEvent(
                saved.getId(), saved.getUsername(), saved.getEmail()));

        log.info("Registered user {} ({})", saved.getUsername(), saved.getId());
        return toResponse(saved);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        UserAccount user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new AppException(HttpStatus.UNAUTHORIZED,
                        "INVALID_CREDENTIALS", "Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new AppException(HttpStatus.UNAUTHORIZED,
                    "INVALID_CREDENTIALS", "Invalid username or password");
        }

        List<String> roles = parseRoles(user.getRoles());
        String token = jwtService.issue(user.getId(), user.getUsername(), roles);
        return new LoginResponse(token, "Bearer", jwtService.getExpiresInSeconds(),
                toResponse(user));
    }

    @Override
    public UserResponse findById(String userId) {
        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,
                        "USER_NOT_FOUND", "User #" + userId + " not found"));
        return toResponse(user);
    }

    private static UserResponse toResponse(UserAccount u) {
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail(),
                u.getFullName(), parseRoles(u.getRoles()));
    }

    private static List<String> parseRoles(String csv) {
        if (csv == null || csv.isBlank()) {
            return List.of("USER");
        }
        return Arrays.stream(csv.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();
    }
}
