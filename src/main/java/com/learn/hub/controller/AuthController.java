package com.learn.hub.controller;

import com.learn.hub.payload.AppResponse;
import com.learn.hub.security.service.AuthService;
import com.learn.hub.security.vo.LoginRequest;
import com.learn.hub.security.vo.LoginResponse;
import com.learn.hub.vo.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AppResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new AppResponse<>(authService.login(loginRequest));

    }

    @PostMapping("/register")
    public AppResponse<UserRequest> register(@RequestBody UserRequest userRequest) {
        return new AppResponse<>(authService.register(userRequest));
    }
}
