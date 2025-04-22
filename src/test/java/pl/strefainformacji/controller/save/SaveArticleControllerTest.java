package pl.strefainformacji.controller.save;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.service.ArticleService;

import java.util.Collections;

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

    
}
