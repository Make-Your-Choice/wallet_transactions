package com.example.project0.configuration;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.InvalidParameterException;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ExceptionMessage> handleException(InvalidParameterException e) {
        ExceptionMessage message = new ExceptionMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionMessage> handleException(IllegalArgumentException e) {
        ExceptionMessage message = new ExceptionMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public static class ExceptionMessage {
        private final String message;
        public ExceptionMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }
}

