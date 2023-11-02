package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class SpecificArticleController {

    private SpecificArticleService specificArticleService;

    @GetMapping("/article/{articleId}")
    public ResponseEntity<?> getOneArticle(@PathVariable Long articleId) {
        try {
            return ResponseEntity.ok(specificArticleService.getArticle(articleId));
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
