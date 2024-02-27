package pl.strefainformacji.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.SpecificArticleService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecificArticleControllerTest {

    @Mock
    private SpecificArticleService specificArticleService;
    @InjectMocks
    private SpecificArticleController specificArticleController;

    @Test
    public void test1(){
        //given
        SpecificArticle specificArticle = new SpecificArticle();
        specificArticle.setTitle("Title");
        specificArticle.setDescription("Description");

        when(specificArticleService.getArticle(1L)).thenReturn(specificArticle);

        //when
        ResponseEntity<?> response = specificArticleController.getOneArticle(1L);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(specificArticle, response.getBody());
    }
}