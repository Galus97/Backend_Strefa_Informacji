package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.service.ArticleInformationService;

import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class ArticleController {


    private final ArticleInformationService articleInformationService;
    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticles(){
        try{
            return ResponseEntity.ok(articleInformationService.getAllArticles());
        } catch(NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
