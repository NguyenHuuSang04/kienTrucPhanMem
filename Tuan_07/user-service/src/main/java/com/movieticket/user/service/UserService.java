package com.movieticket.user.service;

import com.movieticket.user.dto.LoginRequest;
import com.movieticket.user.dto.LoginResponse;
import com.movieticket.user.dto.RegisterRequest;
import com.movieticket.user.dto.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse findById(String userId);
}
