package com.hao.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) throws Exception {
        return super.handleException(ex, request);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleOtherExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>("An internal error occurs, ops.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}