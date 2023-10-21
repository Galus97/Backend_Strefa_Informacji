package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.service.SpecificArticleService;

@RestController
@AllArgsConstructor
public class SpecificArticleController {
    private SpecificArticleService specificArticleService;
    @GetMapping("/oneArticle")
    public ResponseEntity<?> getOneArticle(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "allowedOrigin");
        try{
            return ResponseEntity.ok(specificArticleService.getArticle(1L));
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}