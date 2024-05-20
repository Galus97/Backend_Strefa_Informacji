package pl.strefainformacji.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.SpecificArticleService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleImagesFormController.class)
class ArticleImagesFormControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SpecificArticleService specificArticleService;
//
//    @MockBean
//    private ArticleImagesService articleImagesService;
//
//    @Test
//    public void testArticleImagesFormWithspecificArticleId() throws Exception {
//        Mockito.when(specificArticleService.getSpecificArticleByArticleInformationId(1L)).thenReturn(new ArticleImages().getSpecificArticle());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/add/articleImages?specificArticleId=1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("articleImages"));
//    }
//
//    @Test
//    public void testArticleImagesForm() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/add/articleImages"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("articleImages"));
//    }
}