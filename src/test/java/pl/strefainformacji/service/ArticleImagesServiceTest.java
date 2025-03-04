package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.strefainformacji.entity.Article;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.repository.ArticleImagesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArticleImagesServiceTest {

    @InjectMocks
    private ArticleImagesService articleImagesService;

    @Mock
    private ArticleImagesRepository articleImagesRepository;

    @Mock
    private ArticleImages articleImages;

    @Mock
    private Article specificArticle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveArticleImages_NonNullArticleImages() {
        articleImagesService.saveArticleImages(articleImages);

        verify(articleImagesRepository, times(1)).save(articleImages);
    }

    @Test
    void testSaveArticleImages_NullArticleImages() {
        articleImagesService.saveArticleImages(null);

        verify(articleImagesRepository, never()).save(any(ArticleImages.class));
    }

    @Test
    void testGetAllArticleImagesBySpecificArticle_WithValidSpecificArticle() {
        when(specificArticle.getSpecificArticleId()).thenReturn(1L);
        when(articleImagesRepository.existsBySpecificArticle_SpecificArticleId(1L)).thenReturn(true);
        List<ArticleImages> articleImagesList = Arrays.asList(new ArticleImages(), new ArticleImages());
        when(articleImagesRepository.findAllBySpecificArticle(specificArticle)).thenReturn(articleImagesList);

        List<ArticleImages> result = articleImagesService.getAllArticleImagesBySpecificArticle(specificArticle);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(articleImagesRepository, times(1)).findAllBySpecificArticle(specificArticle);
    }

    @Test
    void testGetAllArticleImagesBySpecificArticle_NoImagesFound() {
        when(specificArticle.getSpecificArticleId()).thenReturn(1L);
        when(articleImagesRepository.existsBySpecificArticle_SpecificArticleId(1L)).thenReturn(false);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            articleImagesService.getAllArticleImagesBySpecificArticle(specificArticle);
        });

        assertEquals("There is no article images in the database.", exception.getMessage());
        verify(articleImagesRepository, never()).findAllBySpecificArticle(specificArticle);
    }

    @Test
    void testGetAllArticleImagesBySpecificArticle_NullSpecificArticle() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            articleImagesService.getAllArticleImagesBySpecificArticle(null);
        });

        assertEquals("Object SpecificArticle is null", exception.getMessage());
        verify(articleImagesRepository, never()).existsBySpecificArticle_SpecificArticleId(anyLong());
        verify(articleImagesRepository, never()).findAllBySpecificArticle(any(Article.class));
    }
}