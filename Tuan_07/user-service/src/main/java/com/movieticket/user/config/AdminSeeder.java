package com.movieticket.user.config;

import com.movieticket.user.domain.UserAccount;
import com.movieticket.user.repository.UserRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Tạo tài khoản admin mặc định khi service start lần đầu.
 * Tránh seed BCrypt hash cứng trong Flyway V1__init.sql vì hash phụ thuộc strength.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "Admin@123";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.existsByUsername(ADMIN_USERNAME)) {
            return;
        }
        Instant now = Instant.now();
        UserAccount admin = UserAccount.builder()
                .id("u-admin")
                .username(ADMIN_USERNAME)
                .email("admin@movieticket.local")
                .passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
                .fullName("System Admin")
                .roles("ADMIN,USER")
                .createdAt(now)
                .updatedAt(now)
                .build();
        userRepository.save(admin);
        log.info("Seeded admin account (username={}, password={})",
                ADMIN_USERNAME, ADMIN_PASSWORD);
    }
}
