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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.service.ArticleService;

import java.util.Collections;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SaveArticleController.class)
class SaveArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private ArticleDto mockArticleDto;
    private MockHttpSession session;
    private TestingAuthenticationToken authToken;

    @BeforeEach
    void setup() {
        authToken = new TestingAuthenticationToken("user", "pass", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        authToken.setAuthenticated(true);

        mockArticleDto = org.mockito.Mockito.mock(ArticleDto.class);
        session = new MockHttpSession();
        session.setAttribute("articleDto", mockArticleDto);
    }

    @Test
    @WithMockUser
    void shouldSaveArticleAndRedirectToSuccess_whenDtoIsValid() throws Exception {
        //given
        when(mockArticleDto.isValid()).thenReturn(true);
        //then
        MvcResult result = mockMvc.perform(post("/save")
                        .with(authentication(authToken))
                        .with(csrf())
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/success"))
                .andReturn();

        verify(articleService).saveFullArticle(mockArticleDto);
        HttpSession resultSession = result.getRequest().getSession(false);
        assert resultSession.getAttribute("articleDto") == null;
    }

    @Test
    @WithMockUser
    void shouldRedirectToError_whenDtoIsInvalid() throws Exception {
        //given
        when(mockArticleDto.isValid()).thenReturn(false);
        //then
        MvcResult result = mockMvc.perform(post("/save")
                        .with(authentication(authToken))
                        .with(csrf())
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"))
                .andReturn();


        verify(articleService, never()).saveFullArticle(mockArticleDto);

        HttpSession resultSession = result.getRequest().getSession(false);
        assert resultSession.getAttribute("articleDto") == mockArticleDto;
    }
}
