package com.learn.hub.controller;

import com.learn.hub.payload.AppResponse;
import com.learn.hub.security.service.AuthService;
import com.learn.hub.security.vo.LoginRequest;
import com.learn.hub.security.vo.LoginResponse;
import com.learn.hub.security.vo.UserRegisterRequest;
import com.learn.hub.security.vo.UserRegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public AppResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new AppResponse<>(authService.login(loginRequest));
    }

    @PostMapping("/auth/register")
    public AppResponse<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest userRequest) {
        return new AppResponse<>(authService.register(userRequest));
    }

    @GetMapping("/account/activate")
    public AppResponse<Void> activateAccount(@RequestParam String code, @RequestParam String email) {
        authService.activateAccount(code, email);
        return new AppResponse<>();
    }
}
