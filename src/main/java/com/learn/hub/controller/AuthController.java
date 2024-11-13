package com.learn.hub.controller;

import com.learn.hub.payload.AppResponse;
import com.learn.hub.security.service.AuthService;
import com.learn.hub.security.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
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
    @PostMapping("/login")
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
    @PostMapping("/register")
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
    @PostMapping("/activate-account")
    public AppResponse<Void> activateAccount(@Valid @RequestBody ActivateAccountRequest request) {
        authService.activateAccount(request);
        return new AppResponse<>();
    }
}
