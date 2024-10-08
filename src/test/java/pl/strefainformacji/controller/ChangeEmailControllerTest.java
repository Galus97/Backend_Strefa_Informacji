package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChangeEmailControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private CurrentEmployee currentEmployee;

    @Mock
    private Employee employee;

    @InjectMocks
    private ChangeEmailController changeEmailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(currentEmployee.getEmployee()).thenReturn(employee);
    }

    @Test
    void testChangeEmailGet_EmailVerified() {
        // Given
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.isEnabledById(1L)).thenReturn(true);

        // When
        String viewName = changeEmailController.changeEmailGet(currentEmployee);

        // Then
        assertEquals("changeEmail", viewName);
        verify(employeeService, times(1)).isEnabledById(1L);
    }


    @Test
    void testChangeEmailGet_EmailNotVerified() {
        // Given
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.isEnabledById(1L)).thenReturn(false);

        // When
        String viewName = changeEmailController.changeEmailGet(currentEmployee);

        // Then
        assertEquals("redirect:verifyEmail", viewName);
        verify(employeeService, times(1)).isEnabledById(1L);
    }

    @Test
    void testChangeEmailPost_EmailsDoNotMatch() {
        // Given
        when(request.getParameter("newEmail")).thenReturn("newemail@example.com");
        when(request.getParameter("newEmailAgain")).thenReturn("differentemail@example.com");

        // When
        String viewName = changeEmailController.changeEmailPost(currentEmployee, request);

        // Then
        assertEquals("changeEmail", viewName);
        verify(employeeService, never()).changeEmail(anyLong(), anyString());
    }

    @Test
    void testChangeEmailPost_EmailMatchesCurrentEmail() {
        // Given
        when(request.getParameter("newEmail")).thenReturn("currentemail@example.com");
        when(request.getParameter("newEmailAgain")).thenReturn("currentemail@example.com");
        when(employee.getEmail()).thenReturn("currentemail@example.com");

        // When
        String viewName = changeEmailController.changeEmailPost(currentEmployee, request);

        // Then
        assertEquals("changeEmail", viewName);
        verify(employeeService, never()).changeEmail(anyLong(), anyString());
    }

    @Test
    void testChangeEmailPost_SuccessfulChange() {
        // Given
        when(request.getParameter("newEmail")).thenReturn("newemail@example.com");
        when(request.getParameter("newEmailAgain")).thenReturn("newemail@example.com");
        when(employee.getEmail()).thenReturn("oldemail@example.com");
        when(employee.getEmployeeId()).thenReturn(1L);

        // When
        String viewName = changeEmailController.changeEmailPost(currentEmployee, request);

        // Then
        assertEquals("redirect:panel", viewName);
        verify(employeeService, times(1)).changeEmail(1L, "newemail@example.com");
        verify(employee, times(1)).setEmail("newemail@example.com");
    }
}