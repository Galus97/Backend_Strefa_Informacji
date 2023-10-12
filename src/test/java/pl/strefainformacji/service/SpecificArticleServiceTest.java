package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.repository.SpecificArticleRepository;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class SpecificArticleServiceTest {

    @InjectMocks
    private SpecificArticleService specificArticleService;

    @Mock
    private SpecificArticleRepository specificArticleRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetArticleWithValidNumber() {
        Long articleNumber = 1L;
        SpecificArticle expectedArticle = new SpecificArticle(); // You can create a test-specific article

        when(specificArticleRepository.existsByArticleInformation_Id(articleNumber)).thenReturn(true);
        when(specificArticleRepository.findByArticleInformation_Id(articleNumber)).thenReturn(expectedArticle);

        SpecificArticle result = specificArticleService.getArticle(articleNumber);

        assertEquals(expectedArticle, result);
    }

    @Test
    public void testGetArticleWithInvalidNumber() {
        Long invalidArticleNumber = -1L;

        // Simulate that the article doesn't exist in the repository
        when(specificArticleRepository.existsByArticleInformation_Id(invalidArticleNumber)).thenReturn(false);

        // Ensure that the service throws the expected exception
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            specificArticleService.getArticle(invalidArticleNumber);
        });

        assertEquals("Artykuł o podanym numerze nie istnieje.", exception.getMessage());
    }

    @Test
    public void testGetArticleWithNonExistentNumber() {
        Long nonExistentArticleNumber = 123L;

        // Simulate that the article doesn't exist in the repository
        when(specificArticleRepository.existsByArticleInformation_Id(nonExistentArticleNumber)).thenReturn(false);

        // Ensure that the service throws the expected exception
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            specificArticleService.getArticle(nonExistentArticleNumber);
        });

        assertEquals("Artykuł o podanym numerze nie istnieje.", exception.getMessage());
    }

    @Test
    public void testGetArticleWithNullNumber() {
        Long nullArticleNumber = null;

        // Ensure that the service throws the expected exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            specificArticleService.getArticle(nullArticleNumber);
        });

        assertEquals("The article number must be greater than zero.", exception.getMessage());
    }
}