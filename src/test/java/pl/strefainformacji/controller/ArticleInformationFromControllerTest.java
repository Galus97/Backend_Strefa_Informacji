package pl.strefainformacji.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.service.ArticleInformationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleInformationFromControllerTest.class)
class ArticleInformationFromControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleInformationService articleInformationService;

    @Test
    public void testArticleInformationForm() throws Exception {
        mockMvc.perform(get("/add/articleInformation"))
                .andExpect(status().isOk())
                .andExpect(view().name("articleInformation"))
                .andExpect(model().attributeExists("articleInformation"));
    }

//    @Test
//    public void testSaveArticleInformationFromForm() throws Exception {
//        ArticleInformation articleInformation = new ArticleInformation();
//        articleInformation.setArticleId(1L);
//
//        when(articleInformationService.saveArticle(articleInformation)).thenReturn(articleInformation);
//
//        mockMvc.perform(post("/add/articleInformation")
//                        .param("articleId", "1"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/specificArticle?articleId=1"));
//    }
}