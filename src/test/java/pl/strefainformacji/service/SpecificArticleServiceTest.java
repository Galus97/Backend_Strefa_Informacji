package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.strefainformacji.component.ErrorMessages;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.exception.SpecificArticleNotFoundException;
import pl.strefainformacji.repository.SpecificArticleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecificArticleServiceTest {

    @Mock
    private SpecificArticleRepository specificArticleRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private SpecificArticleService specificArticleService;

    private SpecificArticle sampleSpecificArticle;

    @BeforeEach
    void setUp() {
        sampleSpecificArticle = SpecificArticle.builder()
                .specificArticleId(1L)
                .title("Sample Title")
                .description("This is a sample description that is long enough.")
                .build();
    }

    @Test
    void testGetSpecificArticleByArticleInformationId_invalidIdNull() {
        // given
        String expectedMessage = "Invalid article id: null";
        when(messageService.getMessage(eq(ErrorMessages.ARTICLE_ID_IS_INVALID), isNull()))
                .thenReturn(expectedMessage);

        // when + then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> specificArticleService.getSpecificArticleByArticleInformationId(null));
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testGetSpecificArticleByArticleInformationId_invalidIdZero() {
        // given
        String expectedMessage = "Invalid article id: 0";
        when(messageService.getMessage(eq(ErrorMessages.ARTICLE_ID_IS_INVALID), eq(0L)))
                .thenReturn(expectedMessage);

        // when + then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> specificArticleService.getSpecificArticleByArticleInformationId(0L));
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testGetSpecificArticleByArticleInformationId_notFound() {
        // given
        Long articleId = 1L;
        String expectedMessage = "Specific article not found: 1";
        when(messageService.getMessage(eq(ErrorMessages.SPECIFIC_ARTICLE_NOT_FOUND), eq(articleId)))
                .thenReturn(expectedMessage);
        when(specificArticleRepository.existsByArticleInformation_ArticleId(articleId)).thenReturn(false);

        // when + then
        SpecificArticleNotFoundException exception = assertThrows(SpecificArticleNotFoundException.class,
                () -> specificArticleService.getSpecificArticleByArticleInformationId(articleId));
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testGetSpecificArticleByArticleInformationId_success() {
        // given
        Long articleId = 1L;
        when(specificArticleRepository.existsByArticleInformation_ArticleId(articleId)).thenReturn(true);
        when(specificArticleRepository.findByArticleInformation_ArticleId(articleId))
                .thenReturn(sampleSpecificArticle);

        // when
        SpecificArticle result = specificArticleService.getSpecificArticleByArticleInformationId(articleId);

        // then
        assertThat(result).isEqualTo(sampleSpecificArticle);
    }

    @Test
    void testSaveSpecificArticle_nullArticle() {
        // given
        String expectedMessage = "Specific article is null";
        when(messageService.getMessage(eq(ErrorMessages.SPECIFIC_ARTICLE_IS_NULL)))
                .thenReturn(expectedMessage);

        // when + then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> specificArticleService.saveSpecificArticle(null));
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testSaveSpecificArticle_success() {
        // when
        specificArticleService.saveSpecificArticle(sampleSpecificArticle);

        // then
        verify(specificArticleRepository, times(1)).save(sampleSpecificArticle);
    }
}
