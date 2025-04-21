package pl.strefainformacji.controller.save;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.EmployeeService;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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


}
