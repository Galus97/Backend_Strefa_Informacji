package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.exception.ArticleNotFoundException;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.SpecificArticleService;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddedArticleControllerTest {
    @Mock
    private SpecificArticleService specificArticleService;

    @Mock
    private ArticleImagesService articleImagesService;

    @Mock
    private Model model;

    @InjectMocks
    private AddedArticleController addedArticleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddedArticlePage_ArticleFound() {
        Long specificArticleId = 1L;

        SpecificArticle specificArticle = mock(SpecificArticle.class);
        ArticleInformation articleInformation = mock(ArticleInformation.class);
        List<ArticleImages> articleImages = Collections.singletonList(mock(ArticleImages.class));

        when(specificArticleService.getSpecificArticleByArticleInformationId(specificArticleId)).thenReturn(specificArticle);
        when(specificArticle.getArticleInformation()).thenReturn(articleInformation);
        when(articleImagesService.getAllArticleImagesBySpecificArticle(specificArticle)).thenReturn(articleImages);

        String viewName = addedArticleController.addedArticlePage(specificArticleId, model);

        assertEquals("viewAddedArticle", viewName);
        verify(model).addAttribute("articleInformation", articleInformation);
        verify(model).addAttribute("specificArticle", specificArticle);
        verify(model).addAttribute("articleImages", articleImages);
    }

    @Test
    public void testAddedArticlePage_ArticleNotFound() {
        Long specificArticleId = null;

        String viewName = addedArticleController.addedArticlePage(specificArticleId, model);

        assertEquals("errorAddedArticle", viewName);
    }

    @Test
    public void testAddedArticlePage_SpecificArticleNull() {
        Long specificArticleId = 1L;

        when(specificArticleService.getSpecificArticleByArticleInformationId(specificArticleId)).thenReturn(null);

        String viewName = addedArticleController.addedArticlePage(specificArticleId, model);

        assertEquals("errorAddedArticle", viewName);
    }

    @Test
    public void testHandlerArticleNotFoundException() {
        ArticleNotFoundException exception = new ArticleNotFoundException("Article not found");

        String viewName = addedArticleController.handlerArticleNotFoundException(exception);

        assertEquals("errorAddedArticle", viewName);
    }
}