package pl.strefainformacji.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.EmployeeService;

@Controller
@RequiredArgsConstructor
public class SpecificArticleFormController {

    private final EmployeeService employeeService;
    public SpecificArticle specificArticle;
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecificArticleFormController.class);

    @GetMapping("/add/specificArticle")
    public String specificArticleForm(Model model, @AuthenticationPrincipal CurrentEmployee curentEmployee) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            specificArticle = new SpecificArticle();
            model.addAttribute("specificArticle", specificArticle);
            return "specificArticle";
        } else {
            return "redirect:/verifyEmail";
        }
    }

    @PostMapping("/add/specificArticle")
    public String saveSpecificArticleFromForm(@Valid SpecificArticle specificArticle, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.error("Error saving specific article information: " + bindingResult.getAllErrors());
            return "specificArticle";
        }
        this.specificArticle = specificArticle;
        return "redirect:articleImages";
    }
}