package pl.strefainformacji.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void shouldReturnPass(){
        //given
        SpecificArticle specificArticle = new SpecificArticle();
        specificArticle.setTitle("Title");
        specificArticle.setDescription("Description");

        when(specificArticleService.getSpecificArticleByArticleInformationId(1L)).thenReturn(specificArticle);

        //when
        ResponseEntity<?> response = specificArticleController.getOneArticle(1L);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(specificArticle, response.getBody());
    }

    @Test
    public void shouldReturnNotFoundWhenArticleNotFound(){
        //given
        when(specificArticleService.getSpecificArticleByArticleInformationId(1L)).thenReturn(null);

        //when
        ResponseEntity<?> response = specificArticleController.getOneArticle(1L);

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}