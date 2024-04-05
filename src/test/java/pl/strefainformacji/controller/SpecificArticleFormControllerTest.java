package pl.strefainformacji.controller;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SpecificArticleFormController.class)
class SpecificArticleFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleInformationService articleInformationService;

    @Test
    public void testSpecificArticleFormWithArticleId() throws Exception {
        when(articleInformationService.findArticleInformation(1L)).thenReturn(new SpecificArticle().getArticleInformation());

        mockMvc.perform(MockMvcRequestBuilders.get("/add/specificArticle?articleId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("specificArticle"))
                .andExpect(model().attributeExists("specificArticle"));
    }

    @Test
    public void testSpecificArticleFormWithoutArticleId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add/specificArticle"))
                .andExpect(status().isOk())
                .andExpect(view().name("articleImages"));
    }
}