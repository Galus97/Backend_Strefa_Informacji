package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServlet;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

import java.net.http.HttpResponse;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class ArticleController {

    private HttpHeaders headers;
    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;

    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticles(){
        headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "allowedOrigin");
        try{
            return ResponseEntity.ok(articleInformationService.getAllArticles());
        } catch(NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/oneArticle")
    public ResponseEntity<?> getOneArticle(){
        headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "allowedOrigin");
        try{
            return ResponseEntity.ok(specificArticleService.getArticle(1L));
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
