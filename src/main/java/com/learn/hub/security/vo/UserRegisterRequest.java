package com.learn.hub.security.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.learn.hub.handler.ErrorCode.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotEmpty(message = FIRST_NAME_REQUIRED)
    @NotNull(message = FIRST_NAME_REQUIRED)
    @Size(min = 3, max = 20, message = NAME_LENGTH_EXCEED)
    private String firstName;
    @NotEmpty(message = LAST_NAME_REQUIRED)
    @NotNull(message = LAST_NAME_REQUIRED)
    @Size(min = 3, max = 20, message = NAME_LENGTH_EXCEED)
    private String lastName;
    @NotNull(message = EMAIL_REQUIRED)
    @NotEmpty(message = EMAIL_REQUIRED)
    @Email(message = EMAIL_INVALID)
    private String email;
    @NotNull(message = PASSWORD_REQUIRED)
    @Size(min = 8, message = EASY_PASSWORD)
    private String password;

}


