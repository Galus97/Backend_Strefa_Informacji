package pl.strefainformacji.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class AllArticlesController {

    private final ArticleInformationService articleInformationService;
    private final EmployeeService employeeService;

    @GetMapping("/all")
    public String showAllArticles(@AuthenticationPrincipal CurrentEmployee currentEmployee, Model model) {
        if (employeeService.isEnabledById(currentEmployee.getEmployee().getEmployeeId())) {
            model.addAttribute("allArticles", articleInformationService.getAllArticles());
            return "allArticles";
        } else {
            return "redirect:verifyEmail";
        }
    }
}