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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        articleInformationService.saveArticle(articleInformation);

        verify(articleInformationRepository, times(1)).save(articleInformation);
    }

    @Test
    void testSaveArticle_NullArticle() {
        articleInformationService.saveArticle(null);

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
}