package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.EmployeeService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArticleImagesFormControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    CurrentEmployee currentEmployee;

    @InjectMocks
    private ArticleImagesFormController articleImagesFormController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testArticleImagesForm_EmployeeEnabled() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(true);

        String viewName = articleImagesFormController.articleImagesForm(currentEmployee);

        assertEquals("articleImages", viewName);
    }

    @Test
    public void testArticleImagesForm_EmployeeNotEnabled() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(false);

        String viewName = articleImagesFormController.articleImagesForm(currentEmployee);

        assertEquals("redirect:/verifyEmail", viewName);
    }

    @Test
    public void testSaveArticleImagesFromForm_ValidData() {
        Map<String, String> allParams = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            allParams.put("imgSrc" + i, "imageSrc" + i);
            allParams.put("altImg" + i, "altText" + i);
        }

        String viewName = articleImagesFormController.saveArticleImagesFromForm(allParams);

        assertEquals("redirect:/article", viewName);
    }

    @Test
    public void testSaveArticleImagesFromForm_NoValidData() {
        Map<String, String> allParams = new HashMap<>();
        allParams.put("imgSrc1", "");
        allParams.put("altImg1", "");

        String viewName = articleImagesFormController.saveArticleImagesFromForm(allParams);

        assertEquals("articleImages", viewName);
    }
}