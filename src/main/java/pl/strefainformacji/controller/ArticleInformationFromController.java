package pl.strefainformacji.controller;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.service.ArticleInformationService;

@Controller
@AllArgsConstructor
public class ArticleInformationFromController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleInformationFromController.class);
    private final ArticleInformationService articleInformationService;
    @GetMapping("/add/articleInformation")
    public String articleInformationForm(Model model){
        model.addAttribute("articleInformation", new ArticleInformation());
        return "articleInformation";
    }
    @PostMapping("/add/articleInformation")
    public String saveArticleInformationFromForm(@Valid ArticleInformation articleInformation, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            logger.error("Error saving article information: " + bindingResult.getAllErrors());
            return "articleInformation";
        }
        articleInformationService.saveArticle(articleInformation);

        return "redirect:specificArticle?articleId=" + articleInformation.getArticleId();
    }
}
