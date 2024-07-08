package pl.strefainformacji.controller;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ArticleInformationFromController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleInformationFromController.class);
    private final ArticleInformationService articleInformationService;
    private final EmployeeService employeeService;
    @GetMapping("/add/articleInformation")
    public String articleInformationForm(Model model, @AuthenticationPrincipal CurrentEmployee curentEmployee){
        Optional<Employee> employee = employeeService.findByEmployeeId(curentEmployee.getEmployee().getEmployeeId());
        ArticleInformation articleInformation = new ArticleInformation();
        articleInformation.setEmployee(employee.get());
        model.addAttribute("articleInformation", articleInformation);
        return "articleInformation";
    }
    @PostMapping("/add/articleInformation")
    public String saveArticleInformationFromForm(@Valid ArticleInformation articleInformation, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            logger.error("Error saving article information: " + bindingResult.getAllErrors());
            return "articleInformation";
        }
        articleInformationService.saveArticle(articleInformation);

        return "redirect:specificArticle?articleId=" + articleInformation.getArticleId();
    }
}
