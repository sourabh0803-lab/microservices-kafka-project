package com.example.demo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
* Global exception handler for Customer Service.
* It catches any uncaught exceptions and returns proper JSON responses.
*/
@RestControllerAdvice
public class GlobalExceptionHandler {
/**
* Handles all RuntimeExceptions
*/
@ExceptionHandler(RuntimeException.class)
public ResponseEntity<String> handleRuntime(RuntimeException ex) {
return new ResponseEntity<>("ERROR: " + ex.getMessage(),
HttpStatus.BAD_REQUEST);
}
/**
* Generic fallback for any other exception
*/
@ExceptionHandler(Exception.class)
public ResponseEntity<String> handleException(Exception ex) {
return new ResponseEntity<>("Something went wrong! " +
ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
}
}