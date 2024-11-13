package com.learn.hub.security.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.learn.hub.handler.ErrorCode.CODE_REQUIRED;
import static com.learn.hub.handler.ErrorCode.USER_ID_REQUIRED;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivateAccountRequest {

    @NotNull(message = USER_ID_REQUIRED)
    @Positive(message = USER_ID_REQUIRED)
    private Long id;
    @NotNull(message = CODE_REQUIRED)
    @NotEmpty(message = CODE_REQUIRED)
    private String code;

}


