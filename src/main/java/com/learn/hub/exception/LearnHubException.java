package com.learn.hub.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class LearnHubException extends BusinessException {

    private String code;
    private String args;
    private HttpStatus status;

    public LearnHubException(String code, HttpStatus status) {
        super(code, status);
        this.code = code;
        this.status = status;
    }

    public LearnHubException(String code, HttpStatus status, String args) {
        super(code, status);
        this.code = code;
        this.status = status;
        this.args = args;
    }

}
