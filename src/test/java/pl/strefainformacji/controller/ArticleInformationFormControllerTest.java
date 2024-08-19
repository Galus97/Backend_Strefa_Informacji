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
import pl.strefainformacji.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArticleInformationFormControllerTest {

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private CurrentEmployee currentEmployee;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private ArticleInformationFormController articleInformationFormController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testArticleInformationForm_EmployeeEnabled() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(true);

        String viewName = articleInformationFormController.articleInformationForm(model, currentEmployee);

        assertEquals("articleInformation", viewName);
        verify(model).addAttribute(eq("articleInformation"), any(ArticleInformation.class));
    }

    @Test
    public void testArticleInformationForm_EmployeeNotEnabled() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(false);

        String viewName = articleInformationFormController.articleInformationForm(model, currentEmployee);

        assertEquals("redirect:/verifyEmail", viewName);
        verify(model, never()).addAttribute(eq("articleInformation"), any(ArticleInformation.class));
    }

    @Test
    public void testSaveArticleInformationFromForm_Success() {
        ArticleInformation articleInformation = new ArticleInformation();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = articleInformationFormController.saveArticleInformationFromForm(articleInformation, bindingResult);

        assertEquals("redirect:specificArticle", viewName);
        assertEquals(articleInformation, articleInformationFormController.articleInformation);
    }

    @Test
    public void testSaveArticleInformationFromForm_Error() {
        ArticleInformation articleInformation = new ArticleInformation();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = articleInformationFormController.saveArticleInformationFromForm(articleInformation, bindingResult);

        assertEquals("articleInformation", viewName);
        verify(bindingResult).getAllErrors();
    }
}