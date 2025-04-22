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
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.service.EmployeeService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SpecificArticleFormController.class)
class SpecificArticleFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private CurrentEmployee currentEmployee;
    private TestingAuthenticationToken authToken;

    @BeforeEach
    void setup() {
        Employee emp = new Employee();
        emp.setEmployeeId(100L);
        currentEmployee = new CurrentEmployee(
                "user",
                "pass",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                emp
        );
        authToken = new TestingAuthenticationToken(
                currentEmployee,
                currentEmployee.getPassword(),
                currentEmployee.getAuthorities()
        );
        authToken.setAuthenticated(true);
    }

    @Test
    void shouldShowForm_whenEmployeeEnabled() throws Exception {
        //given
        when(employeeService.isEnabledById(100L)).thenReturn(true);
        //then
        mockMvc.perform(get("/add/specificArticle").with(authentication(authToken)))
                .andExpect(status().isOk())
                .andExpect(view().name("specificArticle"))
                .andExpect(model().attributeExists("specificArticle"));
    }

    @Test
    void shouldRedirectToVerifyEmail_whenEmployeeNotEnabled() throws Exception {
        //given
        when(employeeService.isEnabledById(100L)).thenReturn(false);
        //then
        mockMvc.perform(get("/add/specificArticle").with(authentication(authToken)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/verifyEmail"));
    }

    @Test
    void shouldSaveSpecificArticleAndRedirect_whenInputValid() throws Exception {
        //given
        when(employeeService.isEnabledById(100L)).thenReturn(true);

        MockHttpSession session = new MockHttpSession();
        SpecificArticle specificArticle = new SpecificArticle();
        //then
        MvcResult result = mockMvc.perform(post("/add/specificArticle")
                        .with(authentication(authToken))
                        .with(csrf())
                        .session(session)
                        .flashAttr("specificArticle", specificArticle))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("articleImages"))
                .andReturn();

        HttpSession httpSession = result.getRequest().getSession(false);
        ArticleDto dto = (ArticleDto) httpSession.getAttribute("articleDto");
        assertNotNull(dto);
        assertSame(specificArticle, dto.getSpecificArticle());
    }
}
