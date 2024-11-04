package com.learn.hub.security.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotEmpty(message = "First name is required")
    @NotNull(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotEmpty(message = "The email is required")
    @NotNull(message = "The email is required")
    @Email(message = "The email is invalid")
    private String email;
    @NotEmpty(message = "The password is required")
    @NotNull(message = "The password is required")
    @Size(min = 8, message = "The password should be at least 8 characters")
    private String password;

}


