// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.manager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import com.assignment4.manager.model.HttpErrorBody;

import reactor.core.publisher.Mono;

// Global exception handler for the Manager service
// Handles ResponseStatusException and returns a structured HTTP error response
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseStatusExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<HttpErrorBody>> handleResponseStatusException(ResponseStatusException ex) {
        return Mono.just(ResponseEntity.status(ex.getStatusCode())
                .body(new HttpErrorBody(ex.getCause() != null ? ex.getCause().getMessage() : ex.getReason())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<HttpErrorBody>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpErrorBody(ex.getMessage())));
    }
}
