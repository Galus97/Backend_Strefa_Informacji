package pl.strefainformacji.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ArticleInformationFormController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleInformationFormController.class);
    private final ArticleInformationService articleInformationService;
    private final EmployeeService employeeService;
    public ArticleInformation articleInformation;
    @GetMapping("/add/articleInformation")
    public String articleInformationForm(Model model, @AuthenticationPrincipal CurrentEmployee curentEmployee){
        if(employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            articleInformation = new ArticleInformation();
            model.addAttribute("articleInformation", articleInformation);
            return "articleInformation";
        } else{
            return "redirect:/verifyEmail";
        }
    }
    @PostMapping("/add/articleInformation")
    public String saveArticleInformationFromForm(@Valid ArticleInformation articleInformation, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            LOGGER.error("Error saving article information: " + bindingResult.getAllErrors());
            return "articleInformation";
        }
        this.articleInformation = articleInformation;
        //articleInformationService.saveArticle(articleInformation);

        return "redirect:specificArticle";
    }
}
