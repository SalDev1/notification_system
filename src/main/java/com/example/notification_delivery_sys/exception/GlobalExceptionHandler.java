package com.example.notification_delivery_sys.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice  // This annotation sync with providing errors by API resources
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Gathers all the errors from ex and pushes it into the map.
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Validation failed")
                .timestamp(new Date())
                .errorCode("TXN-001")
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NotificationNotFoundException ex) {
        Map<String,Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", 404);
        return ResponseEntity.badRequest().body(response);
    }
}
