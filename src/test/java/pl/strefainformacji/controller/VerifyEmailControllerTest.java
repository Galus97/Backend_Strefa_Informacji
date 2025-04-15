package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.EmailService;
import pl.strefainformacji.service.EmployeeService;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(VerifyEmailController.class)
class VerifyEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmailService emailService;

    private CurrentEmployee currentEmployee;
    private Employee mockEmployee;
    private TestingAuthenticationToken authToken;

    @BeforeEach
    void setup() {
        mockEmployee = new Employee();
        mockEmployee.setEmployeeId(1L);
        mockEmployee.setEmail("test@example.com");
        mockEmployee.setEmailCode("1234");

        currentEmployee = mock(CurrentEmployee.class);
        when(currentEmployee.getEmployee()).thenReturn(mockEmployee);

        authToken = new TestingAuthenticationToken(currentEmployee, null, "ROLE_USER");
    }

    @Test
    @WithMockUser
    void shouldReturnVerifyEmailPage_onGet() throws Exception {
        mockMvc.perform(get("/verifyEmail"))
                .andExpect(status().isOk())
                .andExpect(view().name("verifyEmail"));
    }

    @Test
    @WithMockUser
    void shouldRedirectToPanel_whenCorrectCodeSubmitted() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/verifyEmail")
                .param("action", "Wyślij")
                .param("verifyEmailCode", "1234")
                .with(csrf())
                .with(authentication(authToken));
        //then
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("panel"));

        verify(employeeService).updateEnable(1L, true);
    }

    @Test
    @WithMockUser
    void shouldReturnVerifyEmailPage_whenIncorrectCodeSubmitted() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/verifyEmail")
                .param("action", "Wyślij")
                .param("verifyEmailCode", "wrong")
                .with(csrf())
                .with(authentication(authToken));
        //then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("verifyEmail"));

        verify(employeeService, never()).updateEnable(any(), anyBoolean());
    }

    @Test
    @WithMockUser
    void shouldResendCode_whenResendCodeActionCalled() throws Exception {
        //given
        when(emailService.getVerificationCode("test@example.com")).thenReturn("5678");

        MockHttpServletRequestBuilder request = post("/verifyEmail")
                .param("action", "ResendCode")
                .with(csrf())
                .requestAttr("currentEmployee", currentEmployee)
                .with(authentication(authToken))
                .session(new MockHttpSession());
        //then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("verifyEmail"));

        verify(emailService).sendEmail("test@example.com");
        verify(employeeService).updateEmailCode(1L, "5678");
    }
}
