package com.user.user.configuration.exceptionhandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Validation Error");

        // Personalizar el mensaje de error para incluir detalles específicos de la validación
        StringBuilder errorMessage = new StringBuilder("Validation failed for: ");
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMessage.append(error.getField()).append(" (").append(error.getDefaultMessage()).append("), "));
        // Eliminar la coma y el espacio al final
        errorMessage.setLength(errorMessage.length() - 2);
        body.put("message", errorMessage.toString());

        return ResponseEntity.badRequest().body(body);
    }



}