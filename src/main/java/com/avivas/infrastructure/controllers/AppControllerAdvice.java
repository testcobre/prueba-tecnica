package com.avivas.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice("com.avivas.infrastructure.controllers")
public class AppControllerAdvice {
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public final ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
            WebRequest request) {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < ex.getBindingResult().getFieldErrors().size(); i++) {
            var fieldError = ex.getBindingResult().getFieldErrors().get(i);
            message.append(fieldError.getField() + ":" + fieldError.getDefaultMessage());
            message.append(":");
            message.append(fieldError.getDefaultMessage());
            if (i == ex.getBindingResult().getFieldErrors().size() - 1) {
                message.append(", ");
            }
        }
        return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ Exception.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public final ResponseEntity<String> handleMethodArgumentNotValidException(Exception ex,
            WebRequest request) {

        return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }
}
