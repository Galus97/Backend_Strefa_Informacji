package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.CustomEmployeeDetailsService;

@Controller
@AllArgsConstructor
public class ArticleInformationFromController {

    private final ArticleInformationService articleInformationService;

    @GetMapping("/add/articleInformation")
    public String articleInformationForm(Model model, @AuthenticationPrincipal CurrentEmployee curentEmployee){
        ArticleInformation articleInformation = new ArticleInformation();
        articleInformation.setEmployee(curentEmployee.getEmployee());
        model.addAttribute("articleInformation", articleInformation);
        return "articleInformation";
    }

    @PostMapping("/add/articleInformation")
    public String saveArticleInformationFromForm(@Valid ArticleInformation articleInformation, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "articleInformation";
        }
        articleInformationService.saveArticle(articleInformation);

        HttpSession session = request.getSession();
        session.setAttribute("articleId", articleInformation.getArticleId());

        return "redirect:specificArticle";
    }
}
