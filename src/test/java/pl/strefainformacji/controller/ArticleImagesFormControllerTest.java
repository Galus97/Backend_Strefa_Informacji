package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
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

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private ArticleImagesFormController articleImagesFormController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testArticleImagesForm_EmployeeEnabled_SpecificArticle() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(true);
        when(session.getAttribute("Article")).thenReturn("specificArticle");

        String viewName = articleImagesFormController.articleImagesForm(currentEmployee, request);

        assertEquals("articleImages", viewName);
    }

    @Test
    public void testArticleImagesForm_EmployeeEnabled_NoSpecificArticle() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(true);
        when(session.getAttribute("Article")).thenReturn(null);

        String viewName = articleImagesFormController.articleImagesForm(currentEmployee, request);

        assertEquals("redirect:/add/articleInformation", viewName);
    }

    @Test
    public void testArticleImagesForm_EmployeeNotEnabled() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(currentEmployee.getEmployee()).thenReturn(employee);
        when(employeeService.isEnabledById(employee.getEmployeeId())).thenReturn(false);

        String viewName = articleImagesFormController.articleImagesForm(currentEmployee, request);

        assertEquals("redirect:/verifyEmail", viewName);
    }

    @Test
    public void testSaveArticleImagesFromForm_ValidData() {
        Map<String, String> allParams = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            allParams.put("imgSrc" + i, "imageSrc" + i);
            allParams.put("altImg" + i, "altText" + i);
        }

        String viewName = articleImagesFormController.saveArticleImagesFromForm(allParams, model, request);

        verify(session).setAttribute("Article", "articleImages");
        assertEquals("redirect:/article", viewName);
    }

    @Test
    public void testSaveArticleImagesFromForm_NoValidData() {
        Map<String, String> allParams = new HashMap<>();
        allParams.put("imgSrc1", "");
        allParams.put("altImg1", "");

        String viewName = articleImagesFormController.saveArticleImagesFromForm(allParams, model, request);

        verify(model).addAttribute(eq("errorImage"), anyString());
        assertEquals("articleImages", viewName);
    }
}