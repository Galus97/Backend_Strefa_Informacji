package pl.strefainformacji.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;

@Controller
@RequiredArgsConstructor
public class AllArticlesController {

    private final ArticleInformationService articleInformationService;
    private final EmployeeService employeeService;

    @GetMapping("/articles")
    public String getAllArticles(@AuthenticationPrincipal CurrentEmployee curentEmployee, Model model) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            model.addAttribute("allArticles", articleInformationService.getAllArticles());
            return "allArticles";
        } else {
            return "redirect:verifyEmail";
        }
    }
}