package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.model.WeatherDto;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.EmployeeService;
import pl.strefainformacji.service.WeatherService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(PanelController.class)
class PanelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private WeatherService weatherService;

    @MockBean
    private ArticleInformationService articleInformationService;

    private Employee mockEmployee;
    private CurrentEmployee currentEmployee;

    @BeforeEach
    void setup() {
        mockEmployee = new Employee();
        mockEmployee.setEmployeeId(1L);
        mockEmployee.setEmail("test@example.com");

        currentEmployee = new CurrentEmployee(
                "testuser",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                mockEmployee
        );
    }

    @Test
    @WithMockUser
    void shouldShowPanelPageWhenEmployeeEnabled() throws Exception {
        //given
        when(employeeService.isEnabledById(mockEmployee.getEmployeeId())).thenReturn(true);
        WeatherDto weather = WeatherDto.builder()
                .speed(10.5f)
                .pressure(197)
                .humidity(10)
                .temperature(23f)
                .build();
        when(weatherService.getWeather()).thenReturn(weather);
        List<ArticleInformation> dummyArticles = Arrays.asList(new ArticleInformation());
        when(articleInformationService.getLastFiveArticlesByEmployee(mockEmployee)).thenReturn(dummyArticles);

        TestingAuthenticationToken authToken =
                new TestingAuthenticationToken(currentEmployee, null, "ROLE_USER");

        //then
        mockMvc.perform(get("/panel")
                        .with(authentication(authToken)))
                .andExpect(status().isOk())
                .andExpect(view().name("panel"))
                .andExpect(model().attribute("employee", mockEmployee))
                .andExpect(model().attribute("weather", weather))
                .andExpect(model().attribute("lastArticles", dummyArticles));
    }

    @Test
    @WithMockUser
    void shouldRedirectToVerifyEmailWhenEmployeeNotEnabled() throws Exception {
        //given
        when(employeeService.isEnabledById(mockEmployee.getEmployeeId())).thenReturn(false);

        TestingAuthenticationToken authToken =
                new TestingAuthenticationToken(currentEmployee, null, "ROLE_USER");

        //then
        mockMvc.perform(get("/panel")
                        .with(authentication(authToken)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("verifyEmail"));
    }
}
