package com.learn.hub.vo;


import com.learn.hub.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import static com.learn.hub.handler.ErrorCode.*;

@Setter
@Getter
public class UserRequest {

    @NotNull(message = FIRST_NAME_REQUIRED)
    private String firstName;
    @NotNull(message = LAST_NAME_REQUIRED)
    private String lastName;
    @NotNull(message = EMAIL_REQUIRED)
    @Email(message = EMAIL_INVALID)
    private String email;
    @NotNull(message = PASSWORD_REQUIRED)
    private String password;
    private UserRoleEnum role = UserRoleEnum.STUDENT;
}
