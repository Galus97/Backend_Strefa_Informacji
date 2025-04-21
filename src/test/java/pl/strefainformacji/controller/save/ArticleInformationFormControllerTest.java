package pl.strefainformacji.controller.save;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.service.EmployeeService;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ArticleInformationFormController.class)
class ArticleInformationFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private CurrentEmployee currentEmployee;
    private TestingAuthenticationToken authToken;
    private Employee mockEmployee;

    @BeforeEach
    void setup() {
        mockEmployee = new Employee();
        mockEmployee.setEmployeeId(100L);

        currentEmployee = new CurrentEmployee(
                "user", "pass", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), mockEmployee
        );
        authToken = new TestingAuthenticationToken(
                currentEmployee, currentEmployee.getPassword(), currentEmployee.getAuthorities()
        );
        authToken.setAuthenticated(true);
    }

    @Test
    void shouldShowForm_whenEmployeeEnabled() throws Exception {
        //given
        when(employeeService.isEnabledById(100L)).thenReturn(true);
        //then
        mockMvc.perform(get("/add/articleInformation")
                        .with(authentication(authToken)))
                .andExpect(status().isOk())
                .andExpect(view().name("articleInformation"))
                .andExpect(model().attributeExists("articleInformation"));
    }

    @Test
    void shouldRedirectToVerifyEmail_whenEmployeeNotEnabled() throws Exception {
        //given
        when(employeeService.isEnabledById(100L)).thenReturn(false);
        //then
        mockMvc.perform(get("/add/articleInformation")
                        .with(authentication(authToken)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/verifyEmail"));
    }

    @Test
    void shouldReturnForm_whenValidationErrorsPresent() throws Exception {
        //given
        when(employeeService.isEnabledById(100L)).thenReturn(true);
        //then
        mockMvc.perform(post("/add/articleInformation")
                        .with(authentication(authToken))
                        .with(csrf())

                        .param("title", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("articleInformation"))
                .andExpect(model().attributeHasFieldErrors("articleInformation", "title"));
    }

    @Test
    void shouldSaveArticleInformationAndRedirect_whenInputValid() throws Exception {
        //given
        when(employeeService.isEnabledById(100L)).thenReturn(true);
        //then
        MockHttpSession session = new MockHttpSession();
        MvcResult result = mockMvc.perform(post("/add/articleInformation")
                        .with(authentication(authToken))
                        .with(csrf())
                        .session(session)
                        .param("title", "Test Title"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("specificArticle"))
                .andReturn();

        HttpSession resultSession = result.getRequest().getSession(false);
        ArticleDto dto = (ArticleDto) resultSession.getAttribute("articleDto");
        assertNotNull(dto);
        ArticleInformation saved = dto.getArticleInformation();
        assertNotNull(saved);
        assertEquals("Test Title", saved.getTitle());
        assertNotNull(saved.getLocalDateTime());
        assertTrue(saved.getLocalDateTime().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}
