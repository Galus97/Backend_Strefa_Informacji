package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.service.ContentfulService;

@RestController
@AllArgsConstructor
public class ContentfulLastAddedArticleController {

    private final ContentfulService contentfulService;

    @GetMapping("/add/contentful")
    public String contentfullLastArticles() {
         contentfulService.createArticlesFromContentfulArticleDto();

        return "Success";
    }
}
