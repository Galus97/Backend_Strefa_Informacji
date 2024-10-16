package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
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
public class StatisticsController {

    private final EmployeeService employeeService;
    private final ArticleInformationService articleInformationService;

    @GetMapping("/statistics")
    public String specificArticleForm(Model model, @AuthenticationPrincipal CurrentEmployee curentEmployee, HttpServletRequest request) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            return "statistics";
        } else {
            return "redirect:/verifyEmail";
        }
    }
}
