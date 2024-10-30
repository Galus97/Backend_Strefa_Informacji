package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StatisticsControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ArticleInformationService articleInformationService;

    @Mock
    private Model model;

    @Mock
    private CurrentEmployee currentEmployee;

    @Mock
    private Employee employee;

    @InjectMocks
    private StatisticsController statisticsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(currentEmployee.getEmployee()).thenReturn(employee);
    }

    @Test
    void testSpecificArticleForm_EmailVerified() {
        // Given
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.isEnabledById(1L)).thenReturn(true);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<ArticleInformation> articlesList = new ArrayList<>();
        when(articleInformationService.getAddedArticleInPeriod(employee, startOfWeek)).thenReturn(articlesList);

        // When
        String viewName = statisticsController.specificArticleForm(model, currentEmployee);

        // Then
        assertEquals("statistics", viewName);
        verify(model, times(1)).addAttribute("articlesAddedThisWeek", articlesList.size());
    }

    @Test
    void testSpecificArticleForm_EmailNotVerified() {
        // Given
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.isEnabledById(1L)).thenReturn(false);

        // When
        String viewName = statisticsController.specificArticleForm(model, currentEmployee);

        // Then
        assertEquals("redirect:/verifyEmail", viewName);
        verify(model, never()).addAttribute(anyString(), any());
    }
}