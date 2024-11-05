package com.learn.hub.exception;

public class ActivationTokenExpiredException extends RuntimeException {
    public ActivationTokenExpiredException() {
        super("activation token expired");
    }
}
