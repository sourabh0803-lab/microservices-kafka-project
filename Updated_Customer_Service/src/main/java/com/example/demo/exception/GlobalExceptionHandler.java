package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler for Customer Service.
 * This catches all unhandled exceptions and returns meaningful JSON responses.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle Runtime Exceptions (e.g., Product not found from ProductService)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {

        // Log the error with message
        log.error("RuntimeException occurred in Customer Service: {}", ex.getMessage());

        return new ResponseEntity<>(
                "ERROR from Customer Service: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Handle WebClient Exceptions (Product Service Down / Timeout)
     */
    @ExceptionHandler(org.springframework.web.reactive.function.client.WebClientException.class)
    public ResponseEntity<String> handleWebClientException(Exception ex) {

        log.error("WebClient exception occurred: {}", ex.getMessage());

        return new ResponseEntity<>(
                "Product Service is unavailable. Please try later.",
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    /**
     * Handle All Other Exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        // Log full stack trace
        log.error("Unknown error occurred: ", ex);

        return new ResponseEntity<>(
                "Internal server error. Contact support.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
