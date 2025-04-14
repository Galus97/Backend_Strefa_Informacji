package pl.strefainformacji.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import pl.strefainformacji.component.ErrorMessages;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static ResponseEntity<Map<String, String>> getMapResponseEntity(RuntimeException e) {
        Map<String, String> response = new HashMap<>();
        response.put(ErrorMessages.ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
