package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.EmployeeService;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
    private MessageSource messageSource;

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

        when(messageSource.getMessage(eq("error.wrongPassword"), any(), any(Locale.class)))
                .thenReturn("wrongPasswordMsg");
        when(messageSource.getMessage(eq("error.passwordsDoNotMatch"), any(), any(Locale.class)))
                .thenReturn("passwordsDoNotMatchMsg");
    }

    @Test
    void testChangePasswordGet_EmailVerified() {
        //given
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.isEnabledById(1L)).thenReturn(true);
        //when
        String viewName = changePasswordController.showChangePasswordForm(currentEmployee);
        //then
        assertEquals("changePassword", viewName);
        verify(employeeService, times(1)).isEnabledById(1L);
    }

    @Test
    void testChangePasswordGet_EmailNotVerified() {
        //given
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.isEnabledById(1L)).thenReturn(false);
        //when
        String viewName = changePasswordController.showChangePasswordForm(currentEmployee);
        //then
        assertEquals("redirect:verifyEmail", viewName);
        verify(employeeService, times(1)).isEnabledById(1L);
    }

    @Test
    void testChangePasswordPost_WrongOldPassword() {
        //given
        when(request.getParameter("lastPassword")).thenReturn("wrongPassword");
        when(request.getParameter("newPassword")).thenReturn("newPassword123");
        when(request.getParameter("newPasswordAgain")).thenReturn("newPassword123");

        when(employee.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);
        //when
        String viewName = changePasswordController.saveChangedPassword(currentEmployee, request, model);
        //then
        assertEquals("changePassword", viewName);
        verify(model, times(1)).addAttribute(eq("wrongPassword"), eq("wrongPasswordMsg"));
        verify(employeeService, never()).changePassword(anyLong(), anyString());
    }

    @Test
    void testChangePasswordPost_PasswordsDoNotMatch() {
        //given
        when(request.getParameter("lastPassword")).thenReturn("correctPassword");
        when(request.getParameter("newPassword")).thenReturn("newPassword123");
        when(request.getParameter("newPasswordAgain")).thenReturn("differentPassword123");

        when(employee.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches("correctPassword", "encodedPassword")).thenReturn(true);
        //when
        String viewName = changePasswordController.saveChangedPassword(currentEmployee, request, model);
        //then
        assertEquals("changePassword", viewName);
        verify(model, times(1)).addAttribute(eq("passwordsDoNotMatch"), eq("passwordsDoNotMatchMsg"));
        verify(employeeService, never()).changePassword(anyLong(), anyString());
    }

    @Test
    void testChangePasswordPost_SuccessfulPasswordChange() {
        //given
        when(request.getParameter("lastPassword")).thenReturn("correctPassword");
        when(request.getParameter("newPassword")).thenReturn("newPassword123");
        when(request.getParameter("newPasswordAgain")).thenReturn("newPassword123");

        when(employee.getPassword()).thenReturn("encodedPassword");
        when(passwordEncoder.matches("correctPassword", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword123")).thenReturn("encodedNewPassword");
        //when
        String viewName = changePasswordController.saveChangedPassword(currentEmployee, request, model);
        //then
        assertEquals("redirect:panel", viewName);
        verify(employeeService, times(1)).changePassword(employee.getEmployeeId(), "encodedNewPassword");
        verify(employee, times(1)).setPassword("encodedNewPassword");
    }
}
