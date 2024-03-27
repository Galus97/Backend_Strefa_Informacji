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
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

@Controller
@AllArgsConstructor
public class SpecificArticleFormController {

    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;

    @GetMapping("/add/specificArticle")
    public String specificArticleForm(Model model, HttpServletRequest request){
        SpecificArticle specificArticle = new SpecificArticle();
        HttpSession session = request.getSession();

        Long articleId = (Long)session.getAttribute("articleId");
        specificArticle.setArticleInformation(articleInformationService.findArticleInformation(articleId));

        model.addAttribute("specificArticle", specificArticle);
        return "specificArticle";
    }

    @PostMapping("/add/specificArticle")
    public String saveSpecificArticleFromForm(@Valid SpecificArticle specificArticle, BindingResult bindingResult,
                                              HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "specificArticle";
        }
        specificArticleService.saveSpecificArticle(specificArticle);

        HttpSession session = request.getSession();
        session.setAttribute("specificArticleId", specificArticle.getSpecificArticleId());

        return "redirect:articleImages";
    }
}