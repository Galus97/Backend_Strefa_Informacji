package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.SpecificArticleService;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class SpecificArticleController {

    private SpecificArticleService specificArticleService;
    private ArticleImagesService articleImagesService;

    @GetMapping("/article/{articleId}")
    public ResponseEntity<?> getOneArticle(@PathVariable Long articleId) {
        try {
            return ResponseEntity.ok(specificArticleService.getArticle(articleId));
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
