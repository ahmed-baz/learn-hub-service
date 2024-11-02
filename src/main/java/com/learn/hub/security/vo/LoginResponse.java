package com.learn.hub.security.vo;

import com.learn.hub.enums.UserRoleEnum;
import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long id;
    private String email;
    private UserRoleEnum role;
    private String accessToken;

}


