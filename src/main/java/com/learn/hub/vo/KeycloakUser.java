package com.learn.hub.vo;

import com.learn.hub.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
public class KeycloakUser {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private List<UserRoleEnum> roles;
    private Date createdAt;
}
