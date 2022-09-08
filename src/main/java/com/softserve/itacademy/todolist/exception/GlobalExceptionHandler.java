package com.softserve.itacademy.todolist.exception;

import com.softserve.itacademy.todolist.dto.error.SimpleErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        Map<String, List<String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        field -> field.getField().replaceAll(regex, replacement).toLowerCase(),
                        Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
                ));
        return ResponseEntity.badRequest().body(new SimpleErrorResponse<>(errors));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SimpleErrorResponse<>(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleErrorResponse<>(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new SimpleErrorResponse<>(ex.getReason()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new SimpleErrorResponse<>("Request body is not readable"));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("Request for '{} {}' failed with error: {} \n {}", request.getMethod(), request.getRequestURL(), ex.getClass(), ex.getMessage());
        return ResponseEntity.internalServerError().body(new SimpleErrorResponse<>(ex.getMessage()));
    }
}
