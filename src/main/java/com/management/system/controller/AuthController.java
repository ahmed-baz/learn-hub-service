package com.management.system.controller;

import com.management.system.payload.AppResponse;
import com.management.system.security.service.AuthService;
import com.management.system.security.vo.LoginRequest;
import com.management.system.security.vo.LoginResponse;
import com.management.system.vo.UserRequest;
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
    public AppResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new AppResponse<>(authService.login(loginRequest));

    }

    @PostMapping("/register")
    public AppResponse<UserRequest> register(@RequestBody UserRequest userRequest) {
        return new AppResponse<>(authService.register(userRequest));
    }
}
