package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import pl.strefainformacji.component.ErrorMessages;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ArticleInformationNotFoundException;
import pl.strefainformacji.repository.ArticleInformationRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleInformationServiceTest {

    @Mock
    private ArticleInformationRepository articleInformationRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private ArticleInformationService articleInformationService;

    private ArticleInformation sampleArticle;

    @BeforeEach
    void setUp() {
        sampleArticle = ArticleInformation.builder()
                .contentfulId("cid01")
                .title("Sample Title")
                .shortDescription("This is a sample short description.")
                .importance(5)
                .imgSrc("image.jpg")
                .altImg("alt text")
                .localDateTime(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetAllArticles() {
        // given
        List<ArticleInformation> articles = Arrays.asList(sampleArticle);
        when(articleInformationRepository.findAll()).thenReturn(articles);

        // when
        List<ArticleInformation> result = articleInformationService.getAllArticles();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(sampleArticle);
    }

    @Test
    void testGetArticle_invalidIdNull() {
        // when + then
        String expectedMessage = "Invalid article id: null";
        when(messageService.getMessage(eq(ErrorMessages.INVALID_ARTICLE_ID), isNull()))
                .thenReturn(expectedMessage);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> articleInformationService.getArticle(null));
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testGetArticle_invalidIdZero() {
        // when + then
        String expectedMessage = "Invalid article id: 0";
        when(messageService.getMessage(eq(ErrorMessages.INVALID_ARTICLE_ID), eq(0L)))
                .thenReturn(expectedMessage);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> articleInformationService.getArticle(0L));
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testGetArticle_notFound() {
        // given
        Long articleId = 1L;
        String expectedMessage = "Article not found: 1";
        when(messageService.getMessage(eq(ErrorMessages.ARTICLE_NOT_FOUND), eq(articleId)))
                .thenReturn(expectedMessage);
        when(articleInformationRepository.findById(articleId)).thenReturn(Optional.empty());

        // when + then
        ArticleInformationNotFoundException exception = assertThrows(ArticleInformationNotFoundException.class,
                () -> articleInformationService.getArticle(articleId));
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testGetArticle_found() {
        // given
        Long articleId = 1L;
        when(articleInformationRepository.findById(articleId)).thenReturn(Optional.of(sampleArticle));

        // when
        ArticleInformation result = articleInformationService.getArticle(articleId);

        // then
        assertThat(result).isEqualTo(sampleArticle);
    }

    @Test
    void testSaveArticleInformation_nullArticle() {
        // given
        String expectedMessage = "Article is null";
        when(messageService.getMessage(eq(ErrorMessages.ARTICLE_IS_NULL)))
                .thenReturn(expectedMessage);

        // when + then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> articleInformationService.saveArticleInformation(null));
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testSaveArticleInformation_success() {
        // when
        articleInformationService.saveArticleInformation(sampleArticle);

        // then
        verify(articleInformationRepository, times(1)).save(sampleArticle);
    }

    @Test
    void testFindAllArticlesByEmployeeId_invalidId() {
        // given
        Long employeeId = 0L;
        String expectedMessage = "Invalid employee id: 0";
        when(messageService.getMessage(eq(ErrorMessages.INVALID_EMPLOYEE_ID), eq(employeeId)))
                .thenReturn(expectedMessage);

        // when + then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> articleInformationService.findAllArticlesByEmployeeId(employeeId));
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testFindAllArticlesByEmployeeId_success() {
        // given
        Long employeeId = 1L;
        Employee employee = Employee.builder().employeeId(employeeId).build();
        List<ArticleInformation> articles = Arrays.asList(sampleArticle);
        when(employeeService.getEmployee(employeeId)).thenReturn(employee);
        when(articleInformationRepository.findAllByEmployee(employee)).thenReturn(articles);

        // when
        List<ArticleInformation> result = articleInformationService.findAllArticlesByEmployeeId(employeeId);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(sampleArticle);
    }

    @Test
    void testFindAllContentfulIds() {
        // given
        List<String> ids = Arrays.asList("cid01", "cid02", "cid03");
        when(articleInformationRepository.findAllContentfulIds()).thenReturn(ids);

        // when
        List<String> result = articleInformationService.findAllContentfulIds();

        // then
        assertThat(result).containsExactly("cid03", "cid02", "cid01");
    }

    @Test
    void testGetLastFiveArticlesByEmployee() {
        // given
        Employee employee = Employee.builder().employeeId(1L).build();
        List<ArticleInformation> articles = Collections.singletonList(sampleArticle);
        PageRequest pageRequest = PageRequest.of(0, 5);
        when(articleInformationRepository.findLastFiveArticlesByEmployee(employee, pageRequest))
                .thenReturn(articles);

        // when
        List<ArticleInformation> result = articleInformationService.getLastFiveArticlesByEmployee(employee);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(sampleArticle);
    }

    @Test
    void testGetAddedArticleInPeriod() {
        // given
        Employee employee = Employee.builder().employeeId(1L).build();
        LocalDateTime weekStart = LocalDateTime.now().minusDays(1);
        LocalDateTime weekEnd = weekStart.plusDays(7);

        ArticleInformation articleInPeriod = ArticleInformation.builder()
                .contentfulId("cid01")
                .title("In Period")
                .shortDescription("Article in period")
                .importance(5)
                .imgSrc("img.jpg")
                .altImg("alt")
                .localDateTime(weekStart.plusHours(2))
                .build();

        ArticleInformation articleOutPeriod = ArticleInformation.builder()
                .contentfulId("cid02")
                .title("Out Period")
                .shortDescription("Article out period")
                .importance(5)
                .imgSrc("img2.jpg")
                .altImg("alt2")
                .localDateTime(weekEnd.plusHours(1))
                .build();

        List<ArticleInformation> allArticles = Arrays.asList(articleInPeriod, articleOutPeriod);
        when(articleInformationRepository.findAllByEmployee(employee)).thenReturn(allArticles);

        // when
        List<ArticleInformation> result = articleInformationService.getAddedArticleInPeriod(employee, weekStart);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("In Period");
    }
}
