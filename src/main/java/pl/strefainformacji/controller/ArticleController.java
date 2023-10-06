package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class ArticleController {

    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;

    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticles(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "allowedOrigin");
        try{
            return ResponseEntity.ok(articleInformationService.getAllArticles());
        } catch(NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

//    @GetMapping("/articles2")
//    public List<ArticleInformation> getAllArticles2(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Access-Control-Allow-Origin", "allowedOrigin");
//
//        return articleInformationService.getAllArticles();
//    }

    @GetMapping("/oneArticle")
    public ResponseEntity<?> getOneArticle(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "allowedOrigin");
        try{
            return ResponseEntity.ok(specificArticleService.getArticle(1L));
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
