package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.EmailService;
import pl.strefainformacji.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VerifyEmailControllerTest {

    @InjectMocks
    private VerifyEmailController verifyEmailController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmailService emailService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private CurrentEmployee currentEmployee;

    @Mock
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(currentEmployee.getEmployee()).thenReturn(employee);
    }

    @Test
    void testVerifyEmailGet() {
        String viewName = verifyEmailController.verifyEmail();
        assertEquals("verifyEmail", viewName);
    }

    @Test
    void testVerifyEmailPost_SuccessfulVerification() {
        when(request.getParameter("action")).thenReturn("Wyślij");
        when(request.getParameter("verifyEmailCode")).thenReturn("1234");
        when(currentEmployee.getEmployee().getEmailCode()).thenReturn("1234");

        String result = verifyEmailController.verifyEmailPost(request, currentEmployee);
        assertEquals("redirect:panel", result);
        verify(employeeService).updateEnable(currentEmployee.getEmployee().getEmployeeId(), true);
    }

    @Test
    void testVerifyEmailPost_UnsuccessfulVerification() {
        when(request.getParameter("action")).thenReturn("Wyślij");
        when(request.getParameter("verifyEmailCode")).thenReturn("1234");
        when(currentEmployee.getEmployee().getEmailCode()).thenReturn("5678");

        String result = verifyEmailController.verifyEmailPost(request, currentEmployee);
        assertEquals("verifyEmail", result);
        verify(employeeService, never()).updateEnable(anyLong(), anyBoolean());
    }

    @Test
    void testVerifyEmailPost_ResendCode() {
        when(request.getParameter("action")).thenReturn("ResendCode");
        when(emailService.valueOfEmailActiveCode()).thenReturn("newCode");
        when(request.getSession()).thenReturn(session);

        String result = verifyEmailController.verifyEmailPost(request, currentEmployee);
        assertEquals("verifyEmail", result);
        verify(employee).setEmailCode("newCode");
        verify(emailService).sendEmail();
        verify(employeeService).updateEmailCode(currentEmployee.getEmployee().getEmployeeId(), "newCode");
        verify(session).setAttribute("registerEmail", currentEmployee.getEmployee().getEmail());
    }
}