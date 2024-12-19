package com.learn.hub.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learn.hub.utils.NetworkUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppResponse<T> {
    private Date responseDate;
    private Integer statusCode;
    private Map<String, String> validationErrors;
    private T data;
    private Map<String, String> metaData = new HashMap<>() {{
        put("ip-address", NetworkUtil.getHostAddress());
    }};

    public AppResponse() {
        this.responseDate = new Date();
        this.statusCode = HttpStatus.OK.value();
    }

    public AppResponse(HttpStatus status) {
        this.responseDate = new Date();
        this.statusCode = status.value();
    }

    public AppResponse(T t) {
        this.data = t;
        this.responseDate = new Date();
        this.statusCode = HttpStatus.OK.value();
    }

    public AppResponse(T t, HttpStatus status) {
        this.data = t;
        this.responseDate = new Date();
        this.statusCode = status.value();
    }

    public AppResponse(HttpStatus status, Map<String, String> validationErrors) {
        this.responseDate = new Date();
        this.statusCode = status.value();
        this.validationErrors = validationErrors;
    }

}
