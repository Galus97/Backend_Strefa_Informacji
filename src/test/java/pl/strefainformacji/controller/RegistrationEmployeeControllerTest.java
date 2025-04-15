package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ValidationException;
import pl.strefainformacji.service.EmailService;
import pl.strefainformacji.service.RegistrationService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RegistrationEmployeeController.class)
class RegistrationEmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private EmailService emailService;

    private Employee employee;

    @BeforeEach
    void setup() {
        employee = new Employee();
        employee.setUsername("testuser");
        employee.setEmail("test@example.com");
    }

    @Test
    @WithMockUser
    void shouldShowRegisterForm() throws Exception {
        //then
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("employee"));
    }

    @Test
    @WithMockUser
    void shouldRegisterNewEmployeeSuccessfully() throws Exception {
        //given
        String verificationCode = "abc123";

        when(emailService.getVerificationCode("test@example.com")).thenReturn(verificationCode);

        MockHttpServletRequestBuilder request = post("/register")
                .param("username", "testuser")
                .param("email", "test@example.com")
                .with(csrf());

        //then
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"))
                .andExpect(request().sessionAttribute("registerEmail", "test@example.com"));

        verify(emailService).getVerificationCode("test@example.com");
        verify(registrationService).newEmployeeRegistration(any(Employee.class));
        verify(emailService).sendEmail("test@example.com");
    }

    @Test
    @WithMockUser
    void shouldReturnRegisterViewWhenValidationExceptionOccurs() throws Exception {
        //given
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("existEmail", "Email already exists");
        ValidationException validationException = new ValidationException(errorMap);

        doThrow(validationException).when(registrationService).newEmployeeRegistration(any(Employee.class));
        String verificationCode = "abc123";
        when(emailService.getVerificationCode("test@example.com")).thenReturn(verificationCode);

        //then
        MockHttpServletRequestBuilder request = post("/register")
                .param("username", "testuser")
                .param("email", "test@example.com")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("employee", "email"));
        
        verify(emailService).getVerificationCode("test@example.com");
        verify(registrationService).newEmployeeRegistration(any(Employee.class));
        verify(emailService, org.mockito.Mockito.never()).sendEmail("test@example.com");
    }
}
