package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SaveArticleControllerTest {

    @InjectMocks
    private SaveArticleController saveArticleController;

    @Mock
    private ArticleInformationService articleInformationService;

    @Mock
    private SpecificArticleService specificArticleService;

    @Mock
    private ArticleImagesService articleImagesService;

    @Mock
    private ArticleInformationFormController articleInformationFormController;

    @Mock
    private SpecificArticleFormController specificArticleFormController;

    @Mock
    private ArticleImagesFormController articleImagesFormController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private CurrentEmployee currentEmployee;

    @Mock
    private Employee employee;

    @Mock
    private ArticleInformation articleInformation;

    @Mock
    private SpecificArticle specificArticle;

    @Mock
    private ArticleImages articleImages;

    private List<ArticleImages> articleImagesList;

    @BeforeEach
    void setUp() {
        // Inicjalizacja obiektów mockowanych
        MockitoAnnotations.openMocks(this);
        articleImagesList = new ArrayList<>();
        articleImagesList.add(articleImages);

        // Mockowanie zwracanych wartości
        when(articleInformationFormController.articleInformation).thenReturn(articleInformation);
        when(specificArticleFormController.specificArticle).thenReturn(specificArticle);
        when(articleImagesFormController.articleImagesList).thenReturn(articleImagesList);
        when(currentEmployee.getEmployee()).thenReturn(employee);
    }

    @Test
    void testGetArticle_ValidArticle() {
        // Mockowanie sesji i atrybutów
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Article")).thenReturn("articleImages");

        // Mockowanie pól ArticleInformation i SpecificArticle
        when(articleInformation.getContentfulId()).thenReturn("00000");
        when(articleInformation.getTitle()).thenReturn("Title");
        when(articleInformation.getShortDescription()).thenReturn("Short Description");
        when(articleInformation.getImportance()).thenReturn(1);
        when(articleInformation.getImgSrc()).thenReturn("imgSrc.jpg");
        when(articleInformation.getAltImg()).thenReturn("altImg");
        when(articleInformation.getEmployee()).thenReturn(employee);

        when(specificArticle.getTitle()).thenReturn("Specific Article Title");
        when(specificArticle.getDescription()).thenReturn("Specific Article Description");
        when(specificArticle.getArticleInformation()).thenReturn(articleInformation);

        when(articleImages.getImgSrc()).thenReturn("imageSrc.jpg");
        when(articleImages.getAltImg()).thenReturn("altImg");
        when(articleImages.getSpecificArticle()).thenReturn(specificArticle);

        String result = saveArticleController.getArticle(currentEmployee, model, request);

        verify(session).invalidate();
        assertEquals("article", result);

        verify(articleInformationService).saveArticle(articleInformation);
        verify(specificArticleService).saveSpecificArticle(specificArticle);
        verify(articleImagesService).saveArticleImages(articleImages);

        verify(model).addAttribute("articleInformation", articleInformation);
        verify(model).addAttribute("specificArticle", specificArticle);
        verify(model).addAttribute("articleImages", articleImagesList);
    }

    @Test
    void testGetArticle_MissingFields() {
        when(articleInformation.getContentfulId()).thenReturn(null);
        when(specificArticle.getTitle()).thenReturn(null);

        String result = saveArticleController.getArticle(currentEmployee, model, request);

        verify(articleInformationService, never()).saveArticle(any());
        verify(specificArticleService, never()).saveSpecificArticle(any());
        verify(articleImagesService, never()).saveArticleImages(any());

        assertEquals("redirect:/panel", result);
    }

    @Test
    void testGetArticle_SessionNotInvalidated() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("Article")).thenReturn("otherValue");

        String result = saveArticleController.getArticle(currentEmployee, model, request);

        verify(session, never()).invalidate();
        assertEquals("redirect:/panel", result);
    }

//    @Test
//    void testIsEveryFieldsExist_True() {
//        // Mockowanie obecności wszystkich pól
//        when(articleInformation.getContentfulId()).thenReturn("00000");
//        when(articleInformation.getTitle()).thenReturn("Title");
//        when(articleInformation.getShortDescription()).thenReturn("Short Description");
//        when(articleInformation.getImportance()).thenReturn("High");
//        when(articleInformation.getImgSrc()).thenReturn("imgSrc.jpg");
//        when(articleInformation.getAltImg()).thenReturn("altImg");
//        when(articleInformation.getEmployee()).thenReturn(employee);
//
//        when(specificArticle.getTitle()).thenReturn("Specific Article Title");
//        when(specificArticle.getDescription()).thenReturn("Specific Article Description");
//        when(specificArticle.getArticleInformation()).thenReturn(articleInformation);
//
//        when(articleImages.getImgSrc()).thenReturn("imageSrc.jpg");
//        when(articleImages.getAltImg()).thenReturn("altImg");
//        when(articleImages.getSpecificArticle()).thenReturn(specificArticle);
//
//        // Sprawdzenie, że wszystkie pola istnieją
//        boolean result = saveArticleController.isEveryFieldsExist(articleInformation, specificArticle, articleImagesList);
//        assertEquals(true, result);
//    }
//
//    @Test
//    void testIsEveryFieldsExist_False() {
//        // Mockowanie brakujących pól
//        when(articleInformation.getContentfulId()).thenReturn(null);
//        when(specificArticle.getTitle()).thenReturn(null);
//
//        // Sprawdzenie, że brakuje pól
//        boolean result = saveArticleController.isEveryFieldsExist(articleInformation, specificArticle, articleImagesList);
//        assertEquals(false, result);
//    }
}