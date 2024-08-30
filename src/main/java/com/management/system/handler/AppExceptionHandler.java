package com.management.system.handler;


import com.management.system.exception.CourseNotFoundException;
import com.management.system.exception.InvalidCredentialsException;
import com.management.system.payload.AppResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        AppResponse appResponse = new AppResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        ResponseEntity responseEntity = new ResponseEntity(appResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(CourseNotFoundException ex, WebRequest request) {
        AppResponse appResponse = new AppResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler({InvalidCredentialsException.class, BadCredentialsException.class})
    public ResponseEntity<Object> handleAppException(Exception ex, WebRequest request) {
        AppResponse appResponse = new AppResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity(appResponse, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        AppResponse appResponse = new AppResponse(HttpStatus.BAD_REQUEST, ex.getFieldError().getDefaultMessage());
        return new ResponseEntity(appResponse, HttpStatus.OK);
    }


}
