package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class OneArticleControllerTest {

    @InjectMocks
    private OneArticleController controller;

    @Mock
    private SpecificArticleService specificArticleService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void whenValidArticle_thenStatus200() throws Exception {
        SpecificArticle expectedArticle = new SpecificArticle();
        expectedArticle.setArticleInformation(new ArticleInformation());
        expectedArticle.setSpecificArticleId(1L);
        expectedArticle.setTitle("Title");
        expectedArticle.setDescription("Description");
        when(specificArticleService.getSpecificArticleByArticleInformationId(anyLong())).thenReturn(expectedArticle);

        mockMvc.perform(get("/article/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specificArticleId").value(expectedArticle.getSpecificArticleId()))
                .andExpect(jsonPath("$.title").value(expectedArticle.getTitle()))
                .andExpect(jsonPath("$.description").value(expectedArticle.getDescription()));
    }

    @Test
    public void whenInvalidArticle_thenStatus404() throws Exception {
        when(specificArticleService.getSpecificArticleByArticleInformationId(anyLong())).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/article/9999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}