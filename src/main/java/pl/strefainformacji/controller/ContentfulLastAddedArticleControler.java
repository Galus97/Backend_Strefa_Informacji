package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.service.ContentfulService;

@RestController
@AllArgsConstructor
public class ContentfulLastAddedArticleControler {

    private final ContentfulService contentfulService;


    @GetMapping("/contentful")
    public String contentfullLastArticles() {
        return contentfulService.getIdAndTitleOfArticle();
    }
}
