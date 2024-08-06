package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.ContentfulService;
import pl.strefainformacji.service.EmployeeService;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class ContentfulLastAddedArticleControler {

    private final ContentfulService contentfulService;
    private final EmployeeService employeeService;

    @GetMapping("/add/contentful")
    public String contentfullLastArticles(@AuthenticationPrincipal CurrentEmployee curentEmployee) {
        Optional<Employee> employee = employeeService.findByEmployeeId(curentEmployee.getEmployee().getEmployeeId());

        contentfulService.saveArticlesFromContentful(employee.get());
        return "Success";
    }
}
