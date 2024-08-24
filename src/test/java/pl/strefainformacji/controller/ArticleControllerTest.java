package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AllArticlesControllerTest {

    @Mock
    private ArticleInformationService articleInformationService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private Model model;

    @Mock
    private CurrentEmployee currentEmployee;

    @InjectMocks
    private AllArticlesController allArticlesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllArticles_EmployeeEnabled() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        when(currentEmployee.getEmployee()).thenReturn(employee);

        when(employeeService.isEnabledById(1L)).thenReturn(true);

        String viewName = allArticlesController.getAllArticles(currentEmployee, model);

        assertEquals("allArticles", viewName);

        verify(model).addAttribute(eq("allArticles"), any());
    }

    @Test
    void testGetAllArticles_EmployeeNotEnabled() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        when(currentEmployee.getEmployee()).thenReturn(employee);

        when(employeeService.isEnabledById(1L)).thenReturn(false);

        String viewName = allArticlesController.getAllArticles(currentEmployee, model);

        assertEquals("redirect:verifyEmail", viewName);

        verify(model, never()).addAttribute(eq("allArticles"), any());
    }
}