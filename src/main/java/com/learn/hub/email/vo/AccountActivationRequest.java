package com.learn.hub.email.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountActivationRequest {
    private String name;
    private String email;
    private String code;
}