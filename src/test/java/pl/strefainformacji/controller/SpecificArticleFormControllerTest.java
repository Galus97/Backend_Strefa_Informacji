package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
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
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;
import pl.strefainformacji.service.SpecificArticleService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SpecificArticleFormControllerTest {

    @Mock
    private ArticleInformationService articleInformationService;

    @Mock
    private SpecificArticleService specificArticleService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletRequest request;

    @Mock
    private CurrentEmployee currentEmployee;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private SpecificArticleFormController specificArticleFormController;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSpecificArticleForm() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        Long articleId = 1L;

        when(articleInformationService.getArticleInformationByArticleId(articleId)).thenReturn(new ArticleInformation());
        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(true);

        String viewName = specificArticleFormController.specificArticleForm(model, articleId, currentEmployee);
        assertEquals("specificArticle", viewName);
        verify(model).addAttribute(eq("specificArticle"), any(SpecificArticle.class));
    }

    @Test
    public void testSpecificArticleForm_ArticleIdNull() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(true);

        String viewName = specificArticleFormController.specificArticleForm(model, null, currentEmployee);
        assertEquals("articleImages", viewName);
        verify(model, never()).addAttribute(eq("specificArticle"), any(SpecificArticle.class));
    }

    @Test
    public void testSpecificArticleForm_EmployeeNotEnabled() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(false);

        String viewName = specificArticleFormController.specificArticleForm(model, 1L, currentEmployee);
        assertEquals("redirect:verifyEmail", viewName);
        verify(model, never()).addAttribute(eq("specificArticle"), any(SpecificArticle.class));
    }

    @Test
    public void testSaveSpecificArticleFromForm_Success() {
        SpecificArticle specificArticle = new SpecificArticle();
        specificArticle.setSpecificArticleId(1L);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = specificArticleFormController.saveSpecificArticleFromForm(specificArticle, bindingResult, request);
        assertEquals("redirect:articleImages?specificArticleId=" + specificArticle.getSpecificArticleId(), viewName);
        verify(specificArticleService).saveSpecificArticle(any(SpecificArticle.class));
    }

    @Test
    public void testSaveSpecificArticleFromForm_Error() {
        SpecificArticle specificArticle = new SpecificArticle();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = specificArticleFormController.saveSpecificArticleFromForm(specificArticle, bindingResult, request);
        assertEquals("specificArticle", viewName);
        verify(specificArticleService, never()).saveSpecificArticle(any(SpecificArticle.class));
    }
}