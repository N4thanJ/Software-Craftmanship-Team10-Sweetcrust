package be.ucll.team10_bakery.shared.presentation;

import be.ucll.team10_bakery.user.application.UserServiceException;
import be.ucll.team10_bakery.user.domain.UserDomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserDomainException.class)
    public Map<String, String> handleUserDomainException(UserDomainException e) {
        Map<String, String> map = new HashMap<>();
        map.put("field", e.getField());
        map.put("message", e.getMessage());
        return map;
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<Map<String, String>> handleUserServiceException(UserServiceException e) {
        Map<String, String> map = new HashMap<>();
        map.put("field", e.getField());
        map.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(map);
    }
}
