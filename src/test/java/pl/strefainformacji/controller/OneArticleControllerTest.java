package pl.strefainformacji.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(OneArticleController.class)
class OneArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecificArticleService specificArticleService;

    @MockBean
    private ArticleInformationService articleInformationService;

    @MockBean
    private ArticleImagesService articleImagesService;

    @Test
    @WithMockUser
    void shouldReturnOneArticleViewWithModelAttributes() throws Exception {
        // given
        Long articleId = 1L;

        SpecificArticle mockSpecificArticle = new SpecificArticle();
        mockSpecificArticle.setSpecificArticleId(100L);

        ArticleInformation mockArticleInformation = new ArticleInformation();
        mockArticleInformation.setArticleId(articleId);

        ArticleImages mockImage = new ArticleImages();
        mockImage.setArticleImagesId(999L);
        List<ArticleImages> mockImages = Collections.singletonList(mockImage);

        when(specificArticleService.getSpecificArticleByArticleInformationId(articleId)).thenReturn(mockSpecificArticle);
        when(articleInformationService.getArticle(articleId)).thenReturn(mockArticleInformation);
        when(articleImagesService.getAllArticleImagesBySpecificArticle(mockSpecificArticle)).thenReturn(mockImages);

        //then
        mockMvc.perform(get("/article/{articleId}", articleId))
                .andExpect(status().isOk())
                .andExpect(view().name("oneArticle"))
                .andExpect(model().attribute("specificArticle", mockSpecificArticle))
                .andExpect(model().attribute("articleInformation", mockArticleInformation))
                .andExpect(model().attribute("articleImages", mockImages));
    }
}
