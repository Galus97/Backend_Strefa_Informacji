package pl.strefainformacji.controller;

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
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SpecificArticleFormControllerTest {
    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

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

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(true);

        String viewName = specificArticleFormController.specificArticleForm(model, currentEmployee);
        assertEquals("specificArticle", viewName);
        verify(model).addAttribute(eq("specificArticle"), any(SpecificArticle.class));
    }

    @Test
    public void testSpecificArticleForm_EmployeeNotEnabled() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(false);

        String viewName = specificArticleFormController.specificArticleForm(model, currentEmployee);
        assertEquals("redirect:/verifyEmail", viewName);
        verify(model, never()).addAttribute(eq("specificArticle"), any(SpecificArticle.class));
    }

    @Test
    public void testSpecificArticleFromForm_Success() {
        SpecificArticle specificArticle = new SpecificArticle();
        specificArticle.setSpecificArticleId(1L);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = specificArticleFormController.saveSpecificArticleFromForm(specificArticle, bindingResult);
        assertEquals("redirect:articleImages", viewName);
    }

    @Test
    public void testSaveSpecificArticleFromForm_Error() {
        SpecificArticle specificArticle = new SpecificArticle();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = specificArticleFormController.saveSpecificArticleFromForm(specificArticle, bindingResult);
        assertEquals("specificArticle", viewName);
    }
}