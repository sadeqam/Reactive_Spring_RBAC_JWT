package ir.sadeqam.Reactive_Spring_RBAC_JWT.controller.customization;

import ir.sadeqam.Reactive_Spring_RBAC_JWT.service.exceptions.DataNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyExceptionHandler {

    // TODO don't forget logging !!!!
    // return type must be ResponseEntity not mono<ResponseEntity> , double-check this 🥶

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDataNotFoundException(DataNotFoundException e) {
        return new ResponseEntity<>(
                generateResponse(e),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                generateResponse(e),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(
                generateResponse(e),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(
                generateResponse(e),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String, String>> handleGeneralThrowable(Throwable throwable) {
        return new ResponseEntity<>(
                generateResponse(throwable),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private HashMap<String, String> generateResponse(Throwable e) {
        var response = new HashMap<String, String>();
        response.put("time", new Date().toString());
        response.put("message", e.getMessage());
        return response;
    }

}
