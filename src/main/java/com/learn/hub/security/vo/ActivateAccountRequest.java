package com.learn.hub.security.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.learn.hub.handler.ErrorCode.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivateAccountRequest {

    @NotNull(message = EMAIL_REQUIRED)
    @NotEmpty(message = EMAIL_REQUIRED)
    @Email(message = EMAIL_INVALID)
    private String email;
    @NotNull(message = CODE_REQUIRED)
    @NotEmpty(message = CODE_REQUIRED)
    private String code;

}


