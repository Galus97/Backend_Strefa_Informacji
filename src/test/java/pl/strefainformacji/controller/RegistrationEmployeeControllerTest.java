package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ValidationException;
import pl.strefainformacji.service.EmailService;
import pl.strefainformacji.service.RegistrationService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RegistrationEmployeeControllerTest {

    @InjectMocks
    private RegistrationEmployeeController registrationEmployeeController;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private EmailService emailService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterGet() {
        String result = registrationEmployeeController.registerGet(model);

        verify(model).addAttribute(eq("employee"), any(Employee.class));

        assertEquals("register", result);
    }

    @Test
    void testRegisterPost_Valid() throws ValidationException {
        when(bindingResult.hasErrors()).thenReturn(false);

        when(emailService.valueOfEmailActiveCode()).thenReturn("123456");

        when(request.getSession()).thenReturn(session);

        String result = registrationEmployeeController.registerPost(employee, bindingResult, request);

        verify(employee).setEmailCode("123456");

        verify(registrationService).newEmployeeRegistration(employee);

        verify(session).setAttribute("registerEmail", employee.getEmail());
        verify(emailService).sendEmail();

        assertEquals("redirect:login", result);
    }

    @Test
    void testRegisterPost_BindingErrors() throws ValidationException {
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = registrationEmployeeController.registerPost(employee, bindingResult, request);

        assertEquals("register", result);

        verify(registrationService, never()).newEmployeeRegistration(any());
        verify(emailService, never()).sendEmail();
    }

    @Test
    void testRegisterPost_ValidationException() throws ValidationException {
        when(bindingResult.hasErrors()).thenReturn(false);

        ValidationException validationException = new ValidationException(getSampleErrors());
        doThrow(validationException).when(registrationService).newEmployeeRegistration(any(Employee.class));

        String result = registrationEmployeeController.registerPost(employee, bindingResult, request);

        verify(bindingResult).rejectValue("username", "", "Username already exists");
        verify(bindingResult).rejectValue("email", "", "Email already exists");

        assertEquals("register", result);
    }

    private Map<String, String> getSampleErrors() {
        Map<String, String> errors = new HashMap<>();
        errors.put("existUsername", "Username already exists");
        errors.put("existEmail", "Email already exists");
        return errors;
    }
}