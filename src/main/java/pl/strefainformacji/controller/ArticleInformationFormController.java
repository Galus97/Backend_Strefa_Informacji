package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.service.EmployeeService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ArticleInformationFormController {
    private final EmployeeService employeeService;
    public ArticleInformation articleInformation;

    @GetMapping("/add/articleInformation")
    public String articleInformationForm(Model model, @AuthenticationPrincipal CurrentEmployee curentEmployee) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            articleInformation = new ArticleInformation();
            model.addAttribute("articleInformation", articleInformation);
            return "articleInformation";
        } else {
            return "redirect:/verifyEmail";
        }
    }

    @PostMapping("/add/articleInformation")
    public String saveArticleInformationFromForm(@Valid ArticleInformation articleInformation, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "articleInformation";
        }
        articleInformation.setLocalDateTime(LocalDateTime.now());
        this.articleInformation = articleInformation;
        HttpSession session = request.getSession();
        session.setAttribute("Article", "articleInformation");
        return "redirect:specificArticle";
    }
}