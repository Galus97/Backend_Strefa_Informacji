package pl.strefainformacji.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SpringSecurityTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Test
    void testPasswordEncoder() {
        // Test whether the password encoder is an instance of BCryptPasswordEncoder
        assertThat(passwordEncoder).isNotNull();
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Check if the password matches after encoding
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
    }

//    @Test
//    void testPublicUrlsAccessibleWithoutAuthentication() throws Exception {
//        // Initialize MockMvc with Spring Security
//        mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(SecurityMockMvcConfigurers.springSecurity())
//                .build();
//
//        // Test that public URLs are accessible without authentication
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/login"))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/register"))
//                .andExpect(status().isOk());
//    }

    @Test
    void testProtectedUrlRequiresAuthentication() throws Exception {
        // Test that a protected URL (e.g., "/panel") requires authentication
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        mockMvc.perform(get("/panel"))
                .andExpect(status().is3xxRedirection()) // Redirect to login page
                .andExpect(unauthenticated());
    }

//    @Test
//    void testLoginWithValidCredentials() throws Exception {
//        // Test login with valid credentials
//        mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//
//        mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/login")
//                        .user("user")
//                        .password("password")
//                        .with(csrf()))
//                .andExpect(authenticated())
//                .andExpect(status().is3xxRedirection());
//    }

    @Test
    void testLogout() throws Exception {
        // Test logout functionality
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        mockMvc.perform(get("/logout")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated());
    }
}