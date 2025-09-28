package com.replix.office.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Map<String, String>> handleUserCreation(UserCreationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("errorNumber", ex.getErrorNumber().toString());
        error.put("errorCode", ex.getErrorCode());
        error.put("errorDetails", ex.getDetails());
        error.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    /*
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(UserCreationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("errorNumber", "40101");
        error.put("errorCode", "BAD_CREDENTIALS");
        error.put("errorDetails", ex.getMessage());
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    */
}
