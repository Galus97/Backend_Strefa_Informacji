package pl.strefainformacji.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddedArticleController.class)
class AddedArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecificArticleService specificArticleService;

    @MockBean
    private ArticleImagesService articleImagesService;

    @Test
    void testAddedArticlePageWithValidId() throws Exception {
        SpecificArticle specificArticle = new SpecificArticle();
        ArticleInformation articleInformation = new ArticleInformation();
        List<ArticleImages> articleImages = new ArrayList<>();

        when(specificArticleService.getSpecificArticleByArticleInformationId(anyLong()))
                .thenReturn(specificArticle);
        when(articleImagesService.getAllArticleImagesBySpecificArticle(specificArticle))
                .thenReturn(articleImages);

        mockMvc.perform(get("/add/viewAddedArticle")
                        .param("specificArticleId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewAddedArticle"))
                .andExpect(model().attributeExists("articleInformation"))
                .andExpect(model().attributeExists("specificArticle"))
                .andExpect(model().attributeExists("articleImages"));
    }

    @Test
    void testAddedArticlePageWithoutId() throws Exception {
        mockMvc.perform(get("/add/viewAddedArticle"))
                .andExpect(status().isOk())
                .andExpect(view().name("errorAddedArticle"));
    }
}