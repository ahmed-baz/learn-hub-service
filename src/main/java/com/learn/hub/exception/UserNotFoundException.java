package com.learn.hub.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super(String.format("user %s not found", email));
    }
}
