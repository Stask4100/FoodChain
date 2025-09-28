package com.kornievstas.FoodChain.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private String now() {
        return Instant.now().toString();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        ApiError body = new ApiError(now(), 404, "Not Found", ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAlreadyExists(AlreadyExistsException ex, HttpServletRequest req) {
        ApiError body = new ApiError(now(), 409, "Conflict", ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(InvalidActionException.class)
    public ResponseEntity<ApiError> handleInvalidAction(InvalidActionException ex, HttpServletRequest req) {
        ApiError body = new ApiError(now(), 400, "Bad Request", ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String messages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ApiError body = new ApiError(now(), 400, "Validation Failed", messages, req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        ApiError body = new ApiError(now(), 409, "Database error", ex.getMostSpecificCause().getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest req) {
        ApiError body = new ApiError(now(), 500, "Internal Server Error", ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
