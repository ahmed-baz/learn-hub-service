package com.learn.hub.handler;


import com.learn.hub.exception.CourseDocumentException;
import com.learn.hub.exception.CourseNotFoundException;
import com.learn.hub.exception.InvalidCredentialsException;
import com.learn.hub.payload.AppResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
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
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> handleAuthorizationDeniedException(AuthorizationDeniedException ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.FORBIDDEN, ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(CourseNotFoundException ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler(CourseDocumentException.class)
    public ResponseEntity<Object> handleCourseDocumentException(CourseDocumentException ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @ExceptionHandler({InvalidCredentialsException.class, BadCredentialsException.class})
    public ResponseEntity<Object> handleAppException(Exception ex, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        AppResponse<Object> appResponse = new AppResponse<>(HttpStatus.BAD_REQUEST, ex.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }


}
