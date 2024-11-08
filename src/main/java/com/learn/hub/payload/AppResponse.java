package com.learn.hub.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppResponse<T> {
    private Date responseDate;
    private HttpStatus status;
    private String message;
    private String errorCode;
    private Map<String, String> validationErrors;
    private T data;

    public AppResponse() {
        this.responseDate = new Date();
        this.status = HttpStatus.OK;
    }

    public AppResponse(HttpStatus status) {
        this.responseDate = new Date();
        this.status = status;
    }

    public AppResponse(T t) {
        this.data = t;
        this.responseDate = new Date();
        this.status = HttpStatus.OK;
    }

    public AppResponse(T t, HttpStatus status) {
        this.data = t;
        this.responseDate = new Date();
        this.status = status;
    }

    public AppResponse(String message) {
        this.responseDate = new Date();
        this.status = HttpStatus.OK;
        this.message = message;
    }

    public AppResponse(HttpStatus status, String message) {
        this.responseDate = new Date();
        this.status = status;
        this.message = message;
    }

    public AppResponse(HttpStatus status, String message, String errorCode) {
        this.responseDate = new Date();
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    public AppResponse(HttpStatus status, Map<String, String> validationErrors) {
        this.responseDate = new Date();
        this.status = status;
        this.validationErrors = validationErrors;
    }

}
