package com.learn.hub.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class KeycloakUser {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
}
