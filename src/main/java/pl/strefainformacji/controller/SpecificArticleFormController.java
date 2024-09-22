package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/add/specificArticle")
    public String specificArticleForm(Model model, @AuthenticationPrincipal CurrentEmployee curentEmployee, HttpServletRequest request) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            specificArticle = new SpecificArticle();
            HttpSession session = request.getSession();
            if (session.getAttribute("Article") != null && "articleInformation".equals(session.getAttribute("Article"))) {
                model.addAttribute("specificArticle", specificArticle);
                return "specificArticle";
            } else {
                return "redirect:/add/articleInformation";
            }

        } else {
            return "redirect:/verifyEmail";
        }
    }

    @PostMapping("/add/specificArticle")
    public String saveSpecificArticleFromForm(@Valid SpecificArticle specificArticle, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "specificArticle";
        }
        this.specificArticle = specificArticle;
        HttpSession session = request.getSession();
        session.setAttribute("Article", "specificArticle");
        return "redirect:articleImages";
    }
}