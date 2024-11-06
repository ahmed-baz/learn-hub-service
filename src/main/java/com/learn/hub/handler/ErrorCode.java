package com.learn.hub.handler;

public interface ErrorCode {
    String USER_NOT_FOUND = "user_not_found";
    String USER_NOT_AUTHENTICATED = "user_not_authenticated";
    String USER_NOT_AUTHORISED = "user_not_authorised";
    String COURSE_NOT_FOUND = "course_not_found";
    String INTERNAL_SERVER_ERROR = "internal_server_error";
    String EMAIL_EXIST = "email_exist";
    String ACTIVATION_TOKEN_EXPIRED = "activation_token_expired";
    String INVALID_ACTIVATION_TOKEN = "invalid_activation_token";
}
