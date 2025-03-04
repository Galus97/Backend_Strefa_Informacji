package pl.strefainformacji.controller;

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
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.service.EmployeeService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ArticleInformationFormController {
    private final EmployeeService employeeService;

    @GetMapping("/add/articleInformation")
    public String showArticleInformationForm(Model model, @AuthenticationPrincipal CurrentEmployee currentEmployee) {
        if (employeeService.isEnabledById(currentEmployee.getEmployee().getEmployeeId())) {
            model.addAttribute("articleInformation", new ArticleInformation());
            return "articleInformation";
        } else {
            return "redirect:/verifyEmail";
        }
    }

    @PostMapping("/add/articleInformation")
    public String saveArticleInformation(@Valid ArticleInformation articleInformation, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "articleInformation";
        }
        articleInformation.setLocalDateTime(LocalDateTime.now());

        ArticleDto articleDto = (ArticleDto) session.getAttribute("articleDto");
        if (articleDto == null) {
            articleDto = new ArticleDto();
        }

        articleDto.setArticleInformation(articleInformation);
        session.setAttribute("articleDto", articleDto);

        return "redirect:specificArticle";
    }
}