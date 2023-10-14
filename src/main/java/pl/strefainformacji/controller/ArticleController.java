package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@AllArgsConstructor
public class ArticleController {

    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticles(HttpServletResponse response){
        logger.info("Received request to get all articles.");
        response.setHeader("Access-Control-Allow-Origin", "allowedOrigin");
        try{
            List<ArticleInformation> articles = articleInformationService.getAllArticles();
            logger.info("Returning {} articles.", articles.size());
            return ResponseEntity.ok(articleInformationService.getAllArticles());
        } catch(NoSuchElementException exception){
            logger.error("Error while getting articles: {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
