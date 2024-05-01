package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.exception.ArticleNotFoundException;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.List;

@Controller
@AllArgsConstructor
public class AddedArticleController {

    private final SpecificArticleService specificArticleService;
    private final ArticleImagesService articleImagesService;

    @ModelAttribute("specificArticleId")
    public Long specificArticleId(@RequestParam(required = false)Long specificArticleId){
        return specificArticleId;
    }

    @GetMapping("/add/viewAddedArticle")
    public String addedArticlePage(@ModelAttribute("specificArticleId")Long specificArticleId, Model model){

        if(specificArticleId == null){
            handlerArticleNotFoundException(new ArticleNotFoundException("Article not found"));
        }

            SpecificArticle specificArticle = specificArticleService.getSpecificArticleByArticleInformationId(specificArticleId);
            ArticleInformation articleInformation = specificArticle.getArticleInformation();
            List<ArticleImages> articleImages = articleImagesService.getAllArticleImagesBySpecificArticle(specificArticle);

            model.addAttribute("articleInformation", articleInformation);
            model.addAttribute("specificArticle", specificArticle);
            model.addAttribute("articleImages", articleImages);
        return "viewAddedArticle";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public String handlerArticleNotFoundException(ArticleNotFoundException exception){
        exception.getMessage();
        return "errorAddedArticle";
    }
}
