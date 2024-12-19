package com.learn.hub.handler;


import com.learn.hub.exception.LearnHubException;
import com.learn.hub.payload.AppResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, prepareValidationErrors(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }


    @ExceptionHandler(LearnHubException.class)
    public ResponseEntity<Object> handleLearnHubException(LearnHubException ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(ex.getStatus(), prepareValidationErrors(ex.getCode(), getMessage(ex)));
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> validationErrors.put(error.getDefaultMessage(), getMessage(error.getDefaultMessage())));
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.BAD_REQUEST, validationErrors);
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    private Map<String, String> prepareValidationErrors(String code, String message) {
        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put(code, message);
        return validationErrors;
    }

    private String getMessage(LearnHubException ex) {
        try {
            String args = ex.getArgs();
            String message = messageSource.getMessage(ex.getCode(), null, Locale.getDefault());
            if (args != null && message.contains("{}")) {
                message = message.replace("{}", args);
            }
            return message;
        } catch (NoSuchMessageException e) {
            log.error(e.getMessage(), e);
            return ex.getCode();
        }
    }

    private String getMessage(String code) {
        return getMessage(code, Locale.getDefault());
    }

    private String getMessage(String code, Locale locale) {
        try {
            return messageSource.getMessage(code, null, locale);
        } catch (NoSuchMessageException ex) {
            log.error(ex.getMessage(), ex);
            return code;
        }
    }

}
