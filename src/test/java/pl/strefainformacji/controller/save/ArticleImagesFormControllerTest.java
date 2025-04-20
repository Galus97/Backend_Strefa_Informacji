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
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.service.EmployeeService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ArticleImagesFormController.class)
class ArticleImagesFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private MessageService messageService;

    private CurrentEmployee currentEmployee;
    private TestingAuthenticationToken authToken;
    private Employee mockEmployee;

    @BeforeEach
    void setup() {
        mockEmployee = new Employee();
        mockEmployee.setEmployeeId(42L);

        currentEmployee = new CurrentEmployee(
                "user",
                "pass",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                mockEmployee
        );
        authToken = new TestingAuthenticationToken(
                currentEmployee,
                currentEmployee.getPassword(),
                currentEmployee.getAuthorities()
        );
        authToken.setAuthenticated(true);
    }

    @Test
    void shouldShowFormWithTenEmptyImageSlots_whenEmployeeEnabled() throws Exception {
        when(employeeService.isEnabledById(42L)).thenReturn(true);

        MockHttpSession session = new MockHttpSession();
        mockMvc.perform(get("/add/articleImages")
                        .with(authentication(authToken))
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("articleImages"))
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attribute(
                        "form",
                        hasProperty("images", hasSize(10))
                ));
    }

    @Test
    void shouldRedirectToVerifyEmail_whenEmployeeNotEnabled() throws Exception {
        when(employeeService.isEnabledById(42L)).thenReturn(false);

        mockMvc.perform(get("/add/articleImages")
                        .with(authentication(authToken)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/verifyEmail"));
    }

    @Test
    void shouldReturnFormWithFieldErrors_whenInvalidImagesSubmitted() throws Exception {
        when(employeeService.isEnabledById(42L)).thenReturn(true);
        // Blank inputs trigger field-level validation
        when(messageService.getMessage(eq("error.images.min"), eq(null), eq(Locale.ENGLISH)))
                .thenReturn("At least one image required");

        MockHttpSession session = new MockHttpSession();
        mockMvc.perform(post("/add/articleImages")
                        .with(authentication(authToken))
                        .with(csrf())
                        .session(session)
                        .locale(Locale.ENGLISH)
                        // blank src and alt cause NotBlank validation errors
                        .param("images[0].imgSrc", "")
                        .param("images[0].altImg", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("articleImages"))
                .andExpect(model().attributeHasFieldErrors(
                        "form", "images[0].imgSrc", "images[0].altImg"));
    }

    @Test
    void shouldSaveValidImagesAndRedirect_whenAtLeastOneImageProvided() throws Exception {
        when(employeeService.isEnabledById(42L)).thenReturn(true);

        MockHttpSession session = new MockHttpSession();
        MvcResult result = mockMvc.perform(post("/add/articleImages")
                        .with(authentication(authToken))
                        .with(csrf())
                        .session(session)
                        .param("images[0].imgSrc", "http://example.com/img1.png")
                        .param("images[0].altImg", "Example 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/article"))
                .andReturn();

        HttpSession resultSession = result.getRequest().getSession(false);
        ArticleDto articleDto = (ArticleDto) resultSession.getAttribute("articleDto");
        assert articleDto != null;
        List<?> images = articleDto.getImages();
        org.junit.jupiter.api.Assertions.assertEquals(1, images.size());
        org.junit.jupiter.api.Assertions.assertEquals(
                "http://example.com/img1.png",
                ((pl.strefainformacji.entity.ArticleImages) images.get(0)).getImgSrc()
        );
        org.junit.jupiter.api.Assertions.assertEquals(
                "Example 1",
                ((pl.strefainformacji.entity.ArticleImages) images.get(0)).getAltImg()
        );
    }
}
