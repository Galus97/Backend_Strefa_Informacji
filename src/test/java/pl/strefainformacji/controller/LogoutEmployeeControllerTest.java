package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LogoutEmployeeControllerTest {

    @InjectMocks
    private LogoutEmployeeController logoutEmployeeController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testLogout_WithAuthentication() {
        when(securityContext.getAuthentication()).thenReturn(authentication);

        String result = logoutEmployeeController.logout(request, response);

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);

        assertEquals("redirect:/login?logout", result);
    }

    @Test
    void testLogout_WithoutAuthentication() {
        when(securityContext.getAuthentication()).thenReturn(null);

        String result = logoutEmployeeController.logout(request, response);

        assertEquals("redirect:/login?logout", result);
    }
}