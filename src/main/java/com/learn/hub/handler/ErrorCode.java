package com.learn.hub.handler;

public interface ErrorCode {
    String USER_NOT_FOUND = "user_not_found";
    String USER_ID_REQUIRED = "user_id_required";
    String USER_ACCOUNT_DISABLED = "user_account_disabled";
    String USER_NOT_AUTHENTICATED = "user_not_authenticated";
    String USER_NOT_AUTHORISED = "user_not_authorised";
    String COURSE_NOT_FOUND = "course_not_found";
    String INTERNAL_SERVER_ERROR = "internal_server_error";
    String EMAIL_EXIST = "email_exist";
    String ACTIVATION_TOKEN_EXPIRED = "activation_token_expired";
    String INVALID_ACTIVATION_TOKEN = "invalid_activation_token";
    String COURSE_ID_REQUIRED = "course_id_required";
    String COURSE_TITLE_REQUIRED = "course_title_required";
    String COURSE_TITLE_LENGTH_EXCEED = "course_title_length_exceed";
    String COURSE_DESCRIPTION_REQUIRED = "course_description_required";
    String COURSE_DESCRIPTION_LENGTH_EXCEED = "course_description_length_exceed";
    String COURSE_HOURS_REQUIRED = "course_hours_required";
    String COURSE_HOURS_INVALID = "course_hours_invalid";
    String COURSE_START_DATE_REQUIRED = "course_start_date_required";
    String COURSE_START_DATE_INVALID = "course_start_date_invalid";
    String FIRST_NAME_REQUIRED = "first_name_required";
    String LAST_NAME_REQUIRED = "last_name_required";
    String NAME_LENGTH_EXCEED = "name_length_exceed";
    String EMAIL_REQUIRED = "email_required";
    String EMAIL_INVALID = "email_invalid";
    String PASSWORD_REQUIRED = "password_required";
    String EASY_PASSWORD = "easy_password";
    String CODE_REQUIRED = "code_required";
}
