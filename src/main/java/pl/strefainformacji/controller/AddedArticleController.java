package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.List;

@Controller
@AllArgsConstructor
public class AddedArticleController {

    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;
    private final ArticleImagesService articleImagesService;

    @GetMapping("/add/viewAddedArticle")
    public String addedArticlePage(@RequestParam(required = false)Long specificArticleId, Model model){

        if(specificArticleId == null){
            return "errorAddedArticle";
        }

        try{
            SpecificArticle specificArticle = specificArticleService.getArticle(specificArticleId);
            ArticleInformation articleInformation = specificArticle.getArticleInformation();
            List<ArticleImages> articleImages = articleImagesService.findArticleImages(specificArticle);

            model.addAttribute("articleInformation", articleInformation);
            model.addAttribute("specificArticle", specificArticle);
            model.addAttribute("articleImages", articleImages);

        } catch (NullPointerException exception){
            exception.fillInStackTrace();
        }


        return "viewAddedArticle";
    }
}
