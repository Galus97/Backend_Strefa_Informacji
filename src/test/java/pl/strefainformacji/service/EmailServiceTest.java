package pl.strefainformacji.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testSendEmail_Success() {
        when(session.getAttribute("registerEmail")).thenReturn("test@example.com");

        emailService.emailActiveCode = "1234";
        emailService.sendEmail();

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));

        verify(session, times(1)).removeAttribute("registerEmail");
    }

    @Test
    void testSendEmail_NoEmailInSession() {
        when(session.getAttribute("registerEmail")).thenReturn(null);

        emailService.sendEmail();

        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));

        verify(session, never()).removeAttribute("registerEmail");
    }

    @Test
    void testValueOfEmailActiveCode() {
        String generatedCode = emailService.valueOfEmailActiveCode();

        assertNotNull(generatedCode);
        assertTrue(generatedCode.matches("\\d{4}"));
    }

    @Test
    void testGenerateActiveCode() {
        String generatedCode = emailService.valueOfEmailActiveCode();

        assertNotNull(generatedCode);
        int codeAsInt = Integer.parseInt(generatedCode);
        assertTrue(codeAsInt >= 1000 && codeAsInt <= 9999);
    }
}