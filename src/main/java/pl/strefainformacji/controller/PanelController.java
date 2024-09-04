package pl.strefainformacji.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;
import pl.strefainformacji.service.WeatherService;

@Controller
@RequiredArgsConstructor
public class PanelController {

    private final EmployeeService employeeService;
    private final WeatherService weatherService;
    private final ArticleInformationService articleInformationService;

    @GetMapping("/panel")
    public String panel(@AuthenticationPrincipal CurrentEmployee curentEmployee, Model model) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            model.addAttribute("employee", curentEmployee.getEmployee());
            model.addAttribute("weather", weatherService.getWeather());
            model.addAttribute("lastArticles", articleInformationService.getLastFiveArticlesByEmployee(curentEmployee.getEmployee()));
            return "panel";
        } else {
            return "redirect:verifyEmail";
        }
    }
}