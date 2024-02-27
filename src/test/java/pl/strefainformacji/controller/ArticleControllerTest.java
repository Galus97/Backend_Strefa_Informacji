package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.service.ArticleInformationService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @Mock
    private ArticleInformationService articleInformationService;
    @InjectMocks
    private ArticleController articleController;

    @Test
    public void shouldReturnListOfArticles(){
        //given
        List<ArticleInformation> mockArticles = new ArrayList<>();
        ArticleInformation articleInformation1 = new ArticleInformation();
        articleInformation1.setTitle("Title1");
        articleInformation1.setShortDescription("Description1");
        articleInformation1.setImportance(5);
        mockArticles.add(articleInformation1);

        ArticleInformation articleInformation2 = new ArticleInformation();
        articleInformation2.setTitle("Title2");
        articleInformation2.setShortDescription("Description2");
        articleInformation2.setImportance(6);
        mockArticles.add(articleInformation1);


        when(articleInformationService.getAllArticles()).thenReturn(mockArticles);

        //when
        ResponseEntity<?> responseEntity = articleController.getAllArticles();

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(mockArticles, responseEntity.getBody());
    }

    @Test
    public void testGetAllArticlesNotFound() {
        // Arrange
        when(articleInformationService.getAllArticles()).thenThrow(new NoSuchElementException("No articles found"));

        // Act
        ResponseEntity<?> response = articleController.getAllArticles();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No articles found", response.getBody());
    }
}