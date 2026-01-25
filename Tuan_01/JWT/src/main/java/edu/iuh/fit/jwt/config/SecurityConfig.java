package edu.iuh.fit.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Dự án: JWT
 * @Class: SecurityConfig
 * @Tạo vào ngày: 1/25/2026
 * @Tác giả: Nguyen Huu Sang
 */

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF vì dùng Token
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public").permitAll() // Cho phép truy cập tự do
                        .anyRequest().authenticated()               // Các API khác phải có JWT RSA
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}