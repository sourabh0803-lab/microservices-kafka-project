package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for Product Service.
 * It catches all unhandled exceptions and sends meaningful JSON responses.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle RuntimeException (e.g., Product Not Found)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {

        // Logging error
        log.error("RuntimeException occurred: {}", ex.getMessage());

        // Returning custom message with status 400
        return new ResponseEntity<>("ERROR: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle any unknown exception (fallback)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        // Logging error details
        log.error("Unknown Exception occurred: ", ex);

        return new ResponseEntity<>(
                "Internal Server Error. Please contact support.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
