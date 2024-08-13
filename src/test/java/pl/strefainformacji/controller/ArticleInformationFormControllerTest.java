package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ArticleInformationFormControllerTest {
    @Mock
    private ArticleInformationService articleInformationService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    CurrentEmployee currentEmployee;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    private ArticleInformationFormController articleInformationFromController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testArticleInformationForm() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(true);
        when(employeeService.findByEmployeeId(anyLong())).thenReturn(Optional.of(employee));

        String viewName = articleInformationFromController.articleInformationForm(model, currentEmployee);
        assertEquals("articleInformation", viewName);
        verify(model).addAttribute(eq("articleInformation"), any(ArticleInformation.class));
    }

    @Test
    public void testSaveArticleInformationFromForm_Success() {
        ArticleInformation articleInformation = new ArticleInformation();
        articleInformation.setArticleId(1L);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = articleInformationFromController.saveArticleInformationFromForm(articleInformation, bindingResult);
        assertEquals("redirect:specificArticle?articleId=" + articleInformation.getArticleId(), viewName);
        verify(articleInformationService).saveArticle(articleInformation);
    }

    @Test
    public void testSaveArticleInformationFromForm_Error() {
        ArticleInformation articleInformation = new ArticleInformation();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = articleInformationFromController.saveArticleInformationFromForm(articleInformation, bindingResult);
        assertEquals("articleInformation", viewName);
        verify(articleInformationService, never()).saveArticle(any(ArticleInformation.class));
    }
}