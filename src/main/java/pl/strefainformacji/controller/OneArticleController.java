package pl.strefainformacji.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OneArticleController {

    private final SpecificArticleService specificArticleService;
    private final ArticleInformationService articleInformationService;
    private final ArticleImagesService articleImagesService;

    @GetMapping("/article/{articleId}")
    public String getOneArticle(@PathVariable Long articleId, Model model) {
        SpecificArticle specificArticle = specificArticleService.getSpecificArticleByArticleInformationId(articleId);
        ArticleInformation articleInformation = articleInformationService.getArticleInformationByArticleId(articleId);
        List<ArticleImages> articleImages = articleImagesService.getAllArticleImagesBySpecificArticle(specificArticle);

        model.addAttribute("specificArticle", specificArticle);
        model.addAttribute("articleInformation", articleInformation);
        model.addAttribute("articleImages", articleImages);

        return "oneArticle";
    }
}
