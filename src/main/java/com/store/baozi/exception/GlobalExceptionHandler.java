package com.store.baozi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDto>> handleValidation(MethodArgumentNotValidException ex) {
        List<ValidationErrorDto> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> new ValidationErrorDto(err.getField(), err.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    public record ValidationErrorDto(String field, String message) {}
}
