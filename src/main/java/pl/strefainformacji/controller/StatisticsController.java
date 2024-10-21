package pl.strefainformacji.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Controller
@RequiredArgsConstructor
public class StatisticsController {

    private final EmployeeService employeeService;
    private final ArticleInformationService articleInformationService;

    @GetMapping("/statistics")
    public String specificArticleForm(Model model, @AuthenticationPrincipal CurrentEmployee curentEmployee) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            int articlesAddedThisWeek = articleInformationService.getAddedArticleInPeriod(curentEmployee.getEmployee(), startOfWeek).size();
            model.addAttribute("articlesAddedThisWeek", articlesAddedThisWeek);
            return "statistics";
        } else {
            return "redirect:/verifyEmail";
        }
    }
}
