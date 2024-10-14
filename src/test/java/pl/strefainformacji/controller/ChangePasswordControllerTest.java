package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChangePasswordControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Model model;

    @Mock
    private CurrentEmployee currentEmployee;

    @Mock
    private Employee employee;

    @InjectMocks
    private ChangePasswordController changePasswordController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(currentEmployee.getEmployee()).thenReturn(employee);
    }

    @Test
    void testChangePasswordGet_EmailVerified() {
        // Given
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.isEnabledById(1L)).thenReturn(true);

        // When
        String viewName = changePasswordController.changePasswordGet(currentEmployee);

        // Then
        assertEquals("changePassword", viewName);
        verify(employeeService, times(1)).isEnabledById(1L);
    }

    @Test
    void testChangePasswordGet_EmailNotVerified() {
        // Given
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.isEnabledById(1L)).thenReturn(false);

        // When
        String viewName = changePasswordController.changePasswordGet(currentEmployee);

        // Then
        assertEquals("redirect:verifyEmail", viewName);
        verify(employeeService, times(1)).isEnabledById(1L);
    }

    @Test
    void testChangePasswordPost_WrongOldPassword() {
        // Given
        when(request.getParameter("lastPassword")).thenReturn("wrongPassword");
        when(request.getParameter("newPassword")).thenReturn("newPassword123");
        when(request.getParameter("newPasswordAgain")).thenReturn("newPassword123");

        when(employee.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        // When
        String viewName = changePasswordController.changePasswordPost(currentEmployee, request, model);

        // Then
        assertEquals("changePassword", viewName);
        verify(model, times(1)).addAttribute(eq("wrongPassword"), anyString());
        verify(employeeService, never()).changePassword(anyLong(), anyString());
    }

    @Test
    void testChangePasswordPost_PasswordsDoNotMatch() {
        // Given
        when(request.getParameter("lastPassword")).thenReturn("correctPassword");
        when(request.getParameter("newPassword")).thenReturn("newPassword123");
        when(request.getParameter("newPasswordAgain")).thenReturn("differentPassword123");

        when(employee.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches("correctPassword", "encodedPassword")).thenReturn(true);

        // When
        String viewName = changePasswordController.changePasswordPost(currentEmployee, request, model);

        // Then
        assertEquals("changePassword", viewName);
        verify(model, times(1)).addAttribute(eq("passwordsDoNotMatch"), anyString());
        verify(employeeService, never()).changePassword(anyLong(), anyString());
    }

    @Test
    void testChangePasswordPost_SuccessfulPasswordChange() {
        // Given
        when(request.getParameter("lastPassword")).thenReturn("correctPassword");
        when(request.getParameter("newPassword")).thenReturn("newPassword123");
        when(request.getParameter("newPasswordAgain")).thenReturn("newPassword123");

        when(employee.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches("correctPassword", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword123")).thenReturn("encodedNewPassword");

        // When
        String viewName = changePasswordController.changePasswordPost(currentEmployee, request, model);

        // Then
        assertEquals("redirect:panel", viewName);
        verify(employeeService, times(1)).changePassword(employee.getEmployeeId(), "encodedNewPassword");
        verify(employee, times(1)).setPassword("encodedNewPassword");
    }
}