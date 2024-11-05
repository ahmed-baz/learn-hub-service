package com.learn.hub.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class BusinessException extends RuntimeException {

    private String message;
    private String code;
    private Throwable cause;
    private HttpStatus status;

    public BusinessException(String code, HttpStatus status) {
        super(code);
        this.code = code;
    }

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, String code, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.message = message;
        this.code = code;
        this.cause = cause;
        this.status = status;
    }
}
