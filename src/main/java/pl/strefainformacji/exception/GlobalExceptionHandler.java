package pl.strefainformacji.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.strefainformacji.component.ErrorMessages;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ArticleImagesNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleArticleImagesNotFoundException(ArticleImagesNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(ArticleInformationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleArticleInformationNotFoundException(ArticleInformationNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleArticleNotFoundException(ArticleNotFoundException e) {
        return getMapResponseEntity(e);
    }

    @ExceptionHandler(ContentfulIntegrationException.class)
    public ResponseEntity<Map<String, String>> handleContentfulIntegrationException(ContentfulIntegrationException e) {
        return getMapResponseEntity(e);
    }

    private static ResponseEntity<Map<String, String>> getMapResponseEntity(RuntimeException e) {
        Map<String, String> response = new HashMap<>();
        response.put(ErrorMessages.ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
