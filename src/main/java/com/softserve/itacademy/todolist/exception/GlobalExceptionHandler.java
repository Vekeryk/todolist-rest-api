package com.softserve.itacademy.todolist.exception;

import com.softserve.itacademy.todolist.dto.error.SimpleErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, List<String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
                ));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleNullEntityReferenceException(NullEntityReferenceException ex, HttpServletRequest request) {
        log.error("Error for '{} {}' failed with error: {} \n {}",
                request.getMethod(), request.getRequestURL(), ex.getClass(), ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getHttpInputMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new SimpleErrorResponse(ex.getReason()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("Request for '{} {}' failed with error: {} \n {}", request.getMethod(), request.getRequestURL(), ex.getClass(), ex.getMessage());
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
