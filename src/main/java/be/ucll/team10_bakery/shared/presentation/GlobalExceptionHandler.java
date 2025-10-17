package be.ucll.team10_bakery.shared.presentation;

import be.ucll.team10_bakery.user.application.UserServiceException;
import be.ucll.team10_bakery.user.domain.UserDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserDomainException.class)
    public Map<String, String> handleUserDomainException(UserDomainException e) {
        Map<String, String> map = new HashMap<>();
        map.put("field", e.getField());
        map.put("message", e.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserServiceException.class)
    public Map<String, String> handleUserServiceException(UserServiceException e) {
        Map<String, String> map = new HashMap<>();
        map.put("field", e.getField());
        map.put("message", e.getMessage());
        return map;
    }
}
