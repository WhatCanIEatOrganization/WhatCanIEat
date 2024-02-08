package com.example.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

        @ExceptionHandler(UsernameAlreadyTakenException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<String> handleUsernameAlreadyTaken(UsernameAlreadyTakenException exception) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(exception.getMessage());
        }

        @ExceptionHandler(EmailAlreadyTakenException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<String> handleEmailAlreadyTaken(EmailAlreadyTakenException exception) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(exception.getMessage());
        }
}
