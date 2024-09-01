package com.management.system.security.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "email is required")
    @NotEmpty(message = "email is required")
    @Email(message = "invalid email")
    private String email;
    @NotNull(message = "password is required")
    private String password;

}


