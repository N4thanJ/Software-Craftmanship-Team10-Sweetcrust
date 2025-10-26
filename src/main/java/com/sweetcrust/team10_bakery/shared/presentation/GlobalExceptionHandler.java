package com.sweetcrust.team10_bakery.shared.presentation;

import com.sweetcrust.team10_bakery.shared.application.ServiceException;
import com.sweetcrust.team10_bakery.shared.domain.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String, String>> handleServiceExceptions(ServiceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", ex.getField());
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleDomainExceptions(DomainException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", ex.getField());
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}