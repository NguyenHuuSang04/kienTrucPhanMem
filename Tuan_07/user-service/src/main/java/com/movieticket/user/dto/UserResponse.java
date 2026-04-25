package com.movieticket.user.dto;

import java.util.List;

public record UserResponse(
        String userId,
        String username,
        String email,
        String fullName,
        List<String> roles
) {}
