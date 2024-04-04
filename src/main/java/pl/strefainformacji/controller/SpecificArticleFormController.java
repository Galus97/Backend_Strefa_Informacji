package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

@Controller
@AllArgsConstructor
public class SpecificArticleFormController {

    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;

    @GetMapping("/add/specificArticle")
    public String specificArticleForm(Model model, @RequestParam(required = false) Long articleId, HttpServletRequest request) {
        SpecificArticle specificArticle = new SpecificArticle();

        if(articleId != null){
            specificArticle.setArticleInformation(articleInformationService.findArticleInformation(articleId));
            model.addAttribute("specificArticle", specificArticle);
            return "specificArticle";
        } else {
            return "articleImages";
        }

    }

    @PostMapping("/add/specificArticle")
    public String saveSpecificArticleFromForm(@Valid SpecificArticle specificArticle, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "specificArticle";
        }
        specificArticleService.saveSpecificArticle(specificArticle);

        return "redirect:articleImages?specificArticleId=" + specificArticle.getSpecificArticleId();
    }
}
