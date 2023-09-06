package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.repository.ArticleInformationRepository;
import pl.strefainformacji.service.ArticleInformationService;

import java.util.List;

@RestController
@AllArgsConstructor
public class ArticleController {

    private final ArticleInformationService articleInformationService;

    @GetMapping("/articles")
    public List<ArticleInformation> getAllArticles(){
        return articleInformationService.getAllArticles();
    }
}
