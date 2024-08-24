package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;

import java.util.NoSuchElementException;

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
