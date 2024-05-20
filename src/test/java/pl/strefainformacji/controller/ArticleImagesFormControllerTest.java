package pl.strefainformacji.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.http.HttpServletRequest;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ArticleImagesFormControllerTest {

    @Mock
    private ArticleImagesService articleImagesService;

    @Mock
    private SpecificArticleService specificArticleService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ArticleImagesFormController articleImagesFormController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testArticleImagesForm() {
        Long specificArticleId = 1L;
        when(request.getParameter("specificArticleId")).thenReturn(String.valueOf(specificArticleId));

        String viewName = articleImagesFormController.articleImagesForm(specificArticleId, request);

        assertEquals("articleImages", viewName);
        verify(request).setAttribute("specificArticleId", specificArticleId);
    }

    @Test
    public void testSaveArticleImagesFromForm_ValidImages() {
        Long specificArticleId = 1L;
        SpecificArticle specificArticle = mock(SpecificArticle.class);
        when(request.getParameter("specificArticleId")).thenReturn(String.valueOf(specificArticleId));
        when(specificArticleService.getSpecificArticleByArticleInformationId(specificArticleId)).thenReturn(specificArticle);

        Map<String, String> allParams = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            allParams.put("imgSrc" + i, "imageSrc" + i);
            allParams.put("altImg" + i, "altText" + i);
        }

        String viewName = articleImagesFormController.saveArticleImagesFromForm(request, allParams);

        assertEquals("redirect:viewAddedArticle?specificArticleId=" + specificArticleId, viewName);
        verify(articleImagesService, times(3)).saveArticleImages(any(ArticleImages.class));
    }

    @Test
    public void testSaveArticleImagesFromForm_InvalidImages() {
        Long specificArticleId = 1L;
        SpecificArticle specificArticle = mock(SpecificArticle.class);
        when(request.getParameter("specificArticleId")).thenReturn(String.valueOf(specificArticleId));
        when(specificArticleService.getSpecificArticleByArticleInformationId(specificArticleId)).thenReturn(specificArticle);

        Map<String, String> allParams = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            allParams.put("imgSrc" + i, "");
            allParams.put("altImg" + i, "");
        }

        String viewName = articleImagesFormController.saveArticleImagesFromForm(request, allParams);

        assertEquals("redirect:viewAddedArticle?specificArticleId=" + specificArticleId, viewName);
        verify(articleImagesService, never()).saveArticleImages(any(ArticleImages.class));
    }

    @Test
    public void testSaveArticleImagesFromForm_NullSpecificArticleId() {
        when(request.getParameter("specificArticleId")).thenReturn(null);

        String viewName = articleImagesFormController.saveArticleImagesFromForm(request, new HashMap<>());

        assertEquals("badBindingImages", viewName);
    }

    @Test
    public void testSaveArticleImagesFromForm_SpecificArticleNotFound() {
        Long specificArticleId = 1L;
        when(request.getParameter("specificArticleId")).thenReturn(String.valueOf(specificArticleId));
        when(specificArticleService.getSpecificArticleByArticleInformationId(specificArticleId)).thenReturn(null);

        String viewName = articleImagesFormController.saveArticleImagesFromForm(request, new HashMap<>());

        assertEquals("badBindingImages", viewName);
    }

    @Test
    public void testSaveArticleImagesFromForm_InvalidSpecificArticleId() {
        when(request.getParameter("specificArticleId")).thenReturn("invalid");

        String viewName = articleImagesFormController.saveArticleImagesFromForm(request, new HashMap<>());

        assertEquals("badBindingImages", viewName);
    }
}