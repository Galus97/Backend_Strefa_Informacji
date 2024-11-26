package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.repository.ArticleInformationRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArticleInformationServiceTest {

    @InjectMocks
    private ArticleInformationService articleInformationService;

    @Mock
    private ArticleInformationRepository articleInformationRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private Employee employee;

    @Mock
    private ArticleInformation articleInformation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllArticles_WithArticles() {
        List<ArticleInformation> articles = Arrays.asList(new ArticleInformation(), new ArticleInformation());
        when(articleInformationRepository.findAll()).thenReturn(articles);

        List<ArticleInformation> result = articleInformationService.getAllArticles();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(articleInformationRepository, times(1)).findAll();
    }

    @Test
    void testGetAllArticles_NoArticles() {
        when(articleInformationRepository.findAll()).thenReturn(Collections.emptyList());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            articleInformationService.getAllArticles();
        });

        assertEquals("There are no articles in the database", exception.getMessage());
        verify(articleInformationRepository, times(1)).findAll();
    }

    @Test
    void testGetArticleInformationByArticleId_ValidId() {
        when(articleInformationRepository.findArticleInformationByArticleId(1L)).thenReturn(articleInformation);

        ArticleInformation result = articleInformationService.getArticleInformationByArticleId(1L);

        assertNotNull(result);
        verify(articleInformationRepository, times(1)).findArticleInformationByArticleId(1L);
    }

    @Test
    void testGetArticleInformationByArticleId_InvalidId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            articleInformationService.getArticleInformationByArticleId(0L);
        });

        assertEquals("The article number must be greater than zero.", exception.getMessage());
        verify(articleInformationRepository, never()).findArticleInformationByArticleId(anyLong());
    }

    @Test
    void testSaveArticle_NonNullArticle() {
        articleInformationService.saveArticleInformation(articleInformation);

        verify(articleInformationRepository, times(1)).save(articleInformation);
    }

    @Test
    void testSaveArticle_NullArticle() {
        articleInformationService.saveArticleInformation(null);

        verify(articleInformationRepository, never()).save(any(ArticleInformation.class));
    }

    @Test
    void testFindAllArticlesByEmployee_WithValidEmployee() {
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.findByEmployeeId(1L)).thenReturn(Optional.of(employee));
        List<ArticleInformation> articles = Arrays.asList(new ArticleInformation(), new ArticleInformation());
        when(articleInformationRepository.findAllByEmployee(employee)).thenReturn(articles);

        List<ArticleInformation> result = articleInformationService.findAllArticlesByEmployee(employee);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(articleInformationRepository, times(1)).findAllByEmployee(employee);
    }

    @Test
    void testFindAllArticlesByEmployee_WithInvalidEmployee() {
        when(employee.getEmployeeId()).thenReturn(1L);
        when(employeeService.findByEmployeeId(1L)).thenReturn(Optional.empty());

        List<ArticleInformation> result = articleInformationService.findAllArticlesByEmployee(employee);

        assertNull(result);
        verify(articleInformationRepository, never()).findAllByEmployee(any(Employee.class));
    }

    @Test
    void testFindAllContentfulIds() {
        List<String> contentfulIds = Arrays.asList("id111", "id222", "id333");
        when(articleInformationRepository.findAllContentfulIds()).thenReturn(contentfulIds);

        List<String> result = articleInformationService.findAllContentfulIds();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("id333", result.get(0));
        verify(articleInformationRepository, times(1)).findAllContentfulIds();
    }

    @Test
    void testGetLastFiveArticlesByEmployee() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<ArticleInformation> articles = Arrays.asList(new ArticleInformation(), new ArticleInformation());
        when(articleInformationRepository.findLastFiveArticlesByEmployee(employee, pageRequest)).thenReturn(articles);

        List<ArticleInformation> result = articleInformationService.getLastFiveArticlesByEmployee(employee);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(articleInformationRepository, times(1)).findLastFiveArticlesByEmployee(employee, pageRequest);
    }

    @Test
    void testGetAddedArticleInPeriod_NoArticles() {
        // Given
        when(articleInformationRepository.findAllByEmployee(employee)).thenReturn(Collections.emptyList());

        // When
        LocalDateTime weekStart = LocalDateTime.now().withDayOfMonth(1);
        List<ArticleInformation> result = articleInformationService.getAddedArticleInPeriod(employee, weekStart);

        // Then
        assertEquals(0, result.size());
    }

    @Test
    void testGetAddedArticleInPeriod_ArticlesInWeek() {
        // Given
        LocalDateTime weekStart = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime articleDateTime = weekStart.plusDays(2);

        ArticleInformation article = new ArticleInformation();
        article.setLocalDateTime(articleDateTime);

        when(articleInformationRepository.findAllByEmployee(employee)).thenReturn(Collections.singletonList(article));

        // When
        List<ArticleInformation> result = articleInformationService.getAddedArticleInPeriod(employee, weekStart);

        // Then
        assertEquals(1, result.size());
        assertEquals(article, result.get(0));
    }

    @Test
    void testGetAddedArticleInPeriod_ArticlesOutOfWeek() {
        // Given
        LocalDateTime weekStart = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime articleDateTime = weekStart.minusDays(3);

        ArticleInformation article = new ArticleInformation();
        article.setLocalDateTime(articleDateTime);

        when(articleInformationRepository.findAllByEmployee(employee)).thenReturn(Collections.singletonList(article));

        // When
        List<ArticleInformation> result = articleInformationService.getAddedArticleInPeriod(employee, weekStart);

        // Then
        assertEquals(0, result.size());
    }

    @Test
    void testGetAddedArticleInPeriod_NullWeekStart() {
        // Given
        ArticleInformation article = new ArticleInformation();
        article.setLocalDateTime(LocalDateTime.now());
        when(articleInformationRepository.findAllByEmployee(employee)).thenReturn(Collections.singletonList(article));

        // When
        List<ArticleInformation> result = articleInformationService.getAddedArticleInPeriod(employee, null);

        // Then
        assertEquals(0, result.size());
    }

    @Test
    void testGetAddedArticleInPeriod_MultipleArticles() {
        // Given
        LocalDateTime weekStart = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime inWeekDate = weekStart.plusDays(3);
        LocalDateTime outOfWeekDate = weekStart.minusDays(5);

        ArticleInformation articleInWeek = new ArticleInformation();
        articleInWeek.setLocalDateTime(inWeekDate);

        ArticleInformation articleOutOfWeek = new ArticleInformation();
        articleOutOfWeek.setLocalDateTime(outOfWeekDate);

        when(articleInformationRepository.findAllByEmployee(employee))
                .thenReturn(List.of(articleInWeek, articleOutOfWeek));

        // When
        List<ArticleInformation> result = articleInformationService.getAddedArticleInPeriod(employee, weekStart);

        // Then
        assertEquals(1, result.size());
        assertEquals(articleInWeek, result.get(0));
    }
}