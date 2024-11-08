package com.learn.hub.controller;

import com.learn.hub.payload.AppResponse;
import com.learn.hub.security.service.AuthService;
import com.learn.hub.security.vo.LoginRequest;
import com.learn.hub.security.vo.LoginResponse;
import com.learn.hub.security.vo.UserRegisterRequest;
import com.learn.hub.security.vo.UserRegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Authentication",
        description = "Authentication APIs for login, registration, and activating account")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Login API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AppResponse of type LoginResponse",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class)))
            }
    )
    @PostMapping("/auth/login")
    public AppResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new AppResponse<>(authService.login(loginRequest));
    }

    @Operation(
            summary = "register API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AppResponse of type UserRegisterResponse",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserRegisterResponse.class)))
            }
    )
    @PostMapping("/auth/register")
    public AppResponse<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest userRequest) {
        return new AppResponse<>(authService.register(userRequest));
    }


    @Operation(
            summary = "activate account API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AppResponse of type Void",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppResponse.class)))
            }
    )
    @GetMapping("/account/activate")
    public AppResponse<Void> activateAccount(@RequestParam String code, @RequestParam String email) {
        authService.activateAccount(code, email);
        return new AppResponse<>();
    }
}
