package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;

import java.util.List;

@Controller
@AllArgsConstructor
public class ArticlesOfCurrentEmployee {

    private final ArticleInformationService articleInformationService;
    private final EmployeeService employeeService;

    @GetMapping("/yourarticles")
    public String currentEmployeeArticles(@AuthenticationPrincipal CurrentEmployee curentEmployee, Model model){
        if(employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            List<ArticleInformation> allArticlesByEmployee = articleInformationService.findAllArticlesByEmployee(curentEmployee.getEmployee());
            model.addAttribute("allArticlesByEmployee", allArticlesByEmployee);
            model.addAttribute("employee", curentEmployee.getEmployee());
            return "articlesOfCurrentEmployee";
        } else {
            return "redirect:verifyEmail";
        }
    }

}
