package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.service.ArticleInformationService;

import java.util.List;

@Controller
@AllArgsConstructor
public class ArticlesOfCurrentEmployee {

    private final ArticleInformationService articleInformationService;

    @GetMapping("/yourarticles")
    public String currentEmployeeArticles(@AuthenticationPrincipal CurrentEmployee curentEmployee, Model model){
        List<ArticleInformation> allArticlesByEmployee = articleInformationService.findAllArticlesByEmployee(curentEmployee.getEmployee());
        model.addAttribute("allArticlesByEmployee", allArticlesByEmployee);
        model.addAttribute("employee", curentEmployee.getEmployee());
        return "articlesOfCurrentEmployee";
    }

}
