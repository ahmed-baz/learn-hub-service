package com.learn.hub.exception;

public class EmailExistException extends RuntimeException {
    public EmailExistException(String email) {
        super(String.format("email %s already exist", email));
    }
}
