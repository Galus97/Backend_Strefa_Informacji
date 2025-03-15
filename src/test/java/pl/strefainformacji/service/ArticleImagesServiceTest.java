package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.strefainformacji.component.ErrorMessages;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.exception.ArticleImagesNotFoundException;
import pl.strefainformacji.repository.ArticleImagesRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleImagesServiceTest {

    @Mock
    private ArticleImagesRepository articleImagesRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private ArticleImagesService articleImagesService;

    private ArticleImages articleImages;
    private SpecificArticle specificArticle;
    private final Long specificArticleId = 100L;

    @BeforeEach
    void setUp() {
        articleImages = new ArticleImages();
        specificArticle = new SpecificArticle();

        specificArticle.setSpecificArticleId(specificArticleId);
    }
    
    @Test
    void saveArticleImages_WithValidArticleImages_ShouldCallRepositorySave() {
        // Act
        articleImagesService.saveArticleImages(articleImages);

        // Assert
        verify(articleImagesRepository, times(1)).save(articleImages);
    }

    @Test
    void saveArticleImages_WithNullArticleImages_ShouldThrowIllegalArgumentException() {
        String errorMessage = "ArticleImages is null";
        when(messageService.getMessage(ErrorMessages.ARTICLE_IMAGES_IS_NULL)).thenReturn(errorMessage);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> articleImagesService.saveArticleImages(null));

        assertEquals(errorMessage, exception.getMessage());
        verify(articleImagesRepository, never()).save(any());
    }

    @Test
    void getAllArticleImagesBySpecificArticle_WithNullSpecificArticle_ShouldThrowIllegalArgumentException() {
        String errorMessage = "SpecificArticle is null";
        when(messageService.getMessage(ErrorMessages.SPECIFIC_ARTICLE_IS_NULL)).thenReturn(errorMessage);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> articleImagesService.getAllArticleImagesBySpecificArticle(null));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void getAllArticleImagesBySpecificArticle_WhenImagesNotFound_ShouldThrowArticleImagesNotFoundException() {
        String errorMessage = "Article images not found for id: " + specificArticleId;
        when(messageService.getMessage(ErrorMessages.ARTICLE_IMAGES_NOT_FOUND, specificArticleId))
                .thenReturn(errorMessage);
        when(articleImagesRepository.existsBySpecificArticle_SpecificArticleId(specificArticleId)).thenReturn(false);

        ArticleImagesNotFoundException exception = assertThrows(ArticleImagesNotFoundException.class,
                () -> articleImagesService.getAllArticleImagesBySpecificArticle(specificArticle));

        assertEquals(errorMessage, exception.getMessage());
        verify(articleImagesRepository, never()).findAllBySpecificArticle(any());
    }

    @Test
    void getAllArticleImagesBySpecificArticle_WithValidSpecificArticle_ShouldReturnList() {
        ArticleImages image1 = new ArticleImages();
        ArticleImages image2 = new ArticleImages();
        List<ArticleImages> images = Arrays.asList(image1, image2);

        when(articleImagesRepository.existsBySpecificArticle_SpecificArticleId(specificArticleId)).thenReturn(true);
        when(articleImagesRepository.findAllBySpecificArticle(specificArticle)).thenReturn(images);

        List<ArticleImages> result = articleImagesService.getAllArticleImagesBySpecificArticle(specificArticle);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(articleImagesRepository, times(1)).findAllBySpecificArticle(specificArticle);
    }

    @Test
    void throwIfImagesNotFound_WithExistingImages_ShouldNotThrowException() {
        when(articleImagesRepository.existsBySpecificArticle_SpecificArticleId(specificArticleId)).thenReturn(true);
        assertDoesNotThrow(() -> articleImagesService.throwIfImagesNotFound(specificArticleId));
    }

    @Test
    void throwIfImagesNotFound_WithNonExistingImages_ShouldThrowArticleImagesNotFoundException() {
        String errorMessage = "Article images not found for id: " + specificArticleId;
        when(articleImagesRepository.existsBySpecificArticle_SpecificArticleId(specificArticleId)).thenReturn(false);
        when(messageService.getMessage(ErrorMessages.ARTICLE_IMAGES_NOT_FOUND, specificArticleId))
                .thenReturn(errorMessage);

        ArticleImagesNotFoundException exception = assertThrows(ArticleImagesNotFoundException.class,
                () -> articleImagesService.throwIfImagesNotFound(specificArticleId));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void throwIfObjectIsNull_WithNullObject_ShouldThrowIllegalArgumentException() {
        String errorMessage = "Some error message";
        when(messageService.getMessage("Some error message")).thenReturn(errorMessage);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> articleImagesService.throwIfObjectIsNull(null, "Some error message"));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void throwIfObjectIsNull_WithNonNullObject_ShouldNotThrowException() {
        assertDoesNotThrow(() -> articleImagesService.throwIfObjectIsNull(new Object(), "Some error message"));
    }
}
