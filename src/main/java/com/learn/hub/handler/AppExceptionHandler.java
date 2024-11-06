package com.learn.hub.handler;


import com.learn.hub.exception.LearnHubException;
import com.learn.hub.payload.AppResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;


@RestControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleAuthorizationDeniedException(AuthorizationDeniedException ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.FORBIDDEN, getMessage(ErrorCode.USER_NOT_AUTHORISED), ErrorCode.USER_NOT_AUTHORISED);
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler(LearnHubException.class)
    public ResponseEntity<Object> handleLearnHubException(LearnHubException ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(ex.getStatus(), getMessage(ex), ex.getCode());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(Exception ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.UNAUTHORIZED, getMessage(ErrorCode.USER_NOT_AUTHENTICATED), ErrorCode.USER_NOT_AUTHENTICATED);
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.BAD_REQUEST, ex.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    private String getMessage(LearnHubException ex) {
        String args = ex.getArgs();
        String message = messageSource.getMessage(ex.getCode(), null, Locale.getDefault());
        if (args != null) {
            message = message.replace("{}", args);
        }
        return message;
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    private String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

}
