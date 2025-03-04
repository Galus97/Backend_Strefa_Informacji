package pl.strefainformacji.controller.save;

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
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.service.EmployeeService;

@Controller
@RequiredArgsConstructor
public class SpecificArticleFormController {

    private final EmployeeService employeeService;

    @GetMapping("/add/specificArticle")
    public String specificArticleForm(Model model, @AuthenticationPrincipal CurrentEmployee currentEmployee) {
        if (employeeService.isEnabledById(currentEmployee.getEmployee().getEmployeeId())) {
            model.addAttribute("specificArticle", new SpecificArticle());
            return "specificArticle";
        } else {
            return "redirect:/verifyEmail";
        }
    }

    @PostMapping("/add/specificArticle")
    public String saveSpecificArticleFromForm(@Valid SpecificArticle specificArticle, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "specificArticle";
        }
        ArticleDto articleDto = (ArticleDto) session.getAttribute("articleDto");
        if (articleDto == null) {
            articleDto = new ArticleDto();
        }

        articleDto.setSpecificArticle(specificArticle);
        session.setAttribute("articleDto", articleDto);

        return "redirect:articleImages";
    }
}