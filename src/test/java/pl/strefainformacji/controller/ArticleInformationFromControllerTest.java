package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.service.ArticleInformationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ArticleInformationFromControllerTest {
    @Mock
    private ArticleInformationService articleInformationService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ArticleInformationFromController articleInformationFromController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testArticleInformationForm() {
        String viewName = articleInformationFromController.articleInformationForm(model);
        assertEquals("articleInformation", viewName);
        verify(model).addAttribute(eq("articleInformation"), any(ArticleInformation.class));
    }

    @Test
    public void testSaveArticleInformationFromForm_Success() {
        ArticleInformation articleInformation = new ArticleInformation();
        articleInformation.setArticleId(1L);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = articleInformationFromController.saveArticleInformationFromForm(articleInformation, bindingResult);
        assertEquals("redirect:specificArticle?articleId=" + articleInformation.getArticleId(), viewName);
        verify(articleInformationService).saveArticle(articleInformation);
    }

    @Test
    public void testSaveArticleInformationFromForm_Error() {
        ArticleInformation articleInformation = new ArticleInformation();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = articleInformationFromController.saveArticleInformationFromForm(articleInformation, bindingResult);
        assertEquals("articleInformation", viewName);
        verify(articleInformationService, never()).saveArticle(any(ArticleInformation.class));
    }
}