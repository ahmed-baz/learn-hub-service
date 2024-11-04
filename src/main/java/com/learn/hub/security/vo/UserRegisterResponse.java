package com.learn.hub.security.vo;

import com.learn.hub.enums.UserRoleEnum;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRoleEnum role;

}


