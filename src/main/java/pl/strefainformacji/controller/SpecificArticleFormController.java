package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;
import pl.strefainformacji.service.SpecificArticleService;

@Controller
@AllArgsConstructor
public class SpecificArticleFormController {

    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(SpecificArticleFormController.class);

    @GetMapping("/add/specificArticle")
    public String specificArticleForm(Model model, @RequestParam(required = false) Long articleId, @AuthenticationPrincipal CurrentEmployee curentEmployee) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            SpecificArticle specificArticle = new SpecificArticle();

            if (articleId != null) {
                specificArticle.setArticleInformation(articleInformationService.getArticleInformationByArticleId(articleId));
                model.addAttribute("specificArticle", specificArticle);
                return "specificArticle";
            } else {
                return "articleImages";
            }
        } else {
            return "redirect:/verifyEmail";
        }
    }

    @PostMapping("/add/specificArticle")
    public String saveSpecificArticleFromForm(@Valid SpecificArticle specificArticle, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            logger.error("Error saving specific article information: " + bindingResult.getAllErrors());
            return "specificArticle";
        }
        specificArticleService.saveSpecificArticle(specificArticle);

        return "redirect:articleImages?specificArticleId=" + specificArticle.getSpecificArticleId();
    }
}