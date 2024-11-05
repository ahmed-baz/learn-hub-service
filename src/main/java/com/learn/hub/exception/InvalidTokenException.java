package com.learn.hub.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("invalid token");
    }
}
