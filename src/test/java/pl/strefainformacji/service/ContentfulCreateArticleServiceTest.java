package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.webclient.contentful.dto.ContentfulArticleDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContentfulCreateArticleServiceTest {

    @InjectMocks
    private ContentfulCreateArticleService contentfulCreateArticleService;

    @Mock
    private ArticleInformationService articleInformationService;

    @Mock
    private SpecificArticleService specificArticleService;

    @Mock
    private ArticleImagesService articleImagesService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ContentfulService contentfulService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testArticleToAddToDatabase_WhenNoArticlesInDatabase() {
        // Mockowanie pustej bazy danych
        when(articleInformationService.findAllContentfulIds()).thenReturn(new ArrayList<>());
        List<String> contentfulIds = List.of("article1", "article2");
        when(contentfulService.getAllArticlesIds()).thenReturn(contentfulIds);

        // Testowanie
        List<String> result = contentfulCreateArticleService.articleToAddToDatabase();

        // Weryfikacja
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("article1"));
        assertTrue(result.contains("article2"));
    }

    @Test
    void testArticleToAddToDatabase_WhenArticlesExistInDatabase() {
        // Mockowanie artykułów w bazie danych
        List<String> databaseIds = List.of("article1");
        when(articleInformationService.findAllContentfulIds()).thenReturn(databaseIds);
        List<String> contentfulIds = List.of("article1", "article2");
        when(contentfulService.getAllArticlesIds()).thenReturn(contentfulIds);

        // Testowanie
        List<String> result = contentfulCreateArticleService.articleToAddToDatabase();

        // Weryfikacja
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("article2"));
    }

    @Test
    void testContentfulArticleDtoList() {
        // Mockowanie artykułów do dodania
        List<String> articleIdsToAdd = List.of("article1", "article2");
        when(contentfulCreateArticleService.articleToAddToDatabase()).thenReturn(articleIdsToAdd);

        // Mockowanie zwracanych artykułów z Contentful
        ContentfulArticleDto articleDto1 = mock(ContentfulArticleDto.class);
        ContentfulArticleDto articleDto2 = mock(ContentfulArticleDto.class);
        when(contentfulService.getArticleById("article1")).thenReturn(articleDto1);
        when(contentfulService.getArticleById("article2")).thenReturn(articleDto2);

        // Testowanie
        List<ContentfulArticleDto> result = contentfulCreateArticleService.contentfulArticleDtoList();

        // Weryfikacja
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(articleDto1, result.get(0));
        assertEquals(articleDto2, result.get(1));
    }

    @Test
    void testCreateArticlesFromContentfulArticleDto() {
        // Mockowanie artykułów
        ContentfulArticleDto.Fields fields = mock(ContentfulArticleDto.Fields.class);
        when(fields.getEmployeeId()).thenReturn(1);
        when(fields.getHeadTitle()).thenReturn("Sample Title");
        when(fields.getShortDescription()).thenReturn("Sample Description in ArticleInformation");
        when(fields.getImportance()).thenReturn(5);
        when(fields.getHeadImgSrc()).thenReturn(new ContentfulArticleDto.Fields.Sys());
        when(fields.getSpecificTitle()).thenReturn("Specific Title");
        when(fields.getDescription()).thenReturn("Long sample description in SpecificArticle");
        when(fields.getImgSrcList()).thenReturn(new ArrayList<>());
        when(fields.getAltImgList()).thenReturn(new ArrayList<>());

        ContentfulArticleDto articleDto = new ContentfulArticleDto();
        articleDto.setFields(fields);

        List<ContentfulArticleDto> articleDtoList = List.of(articleDto);
        when(contentfulCreateArticleService.contentfulArticleDtoList()).thenReturn(articleDtoList);

        // Mockowanie pracownika
        Employee employee = mock(Employee.class);
        when(employeeService.findByEmployeeId(1L)).thenReturn(Optional.of(employee));

        // Testowanie
        contentfulCreateArticleService.createArticlesFromContentfulArticleDto();

        // Weryfikacja
        verify(articleInformationService, times(1)).saveArticle(any(ArticleInformation.class));
        verify(specificArticleService, times(1)).saveSpecificArticle(any(SpecificArticle.class));
        verify(articleImagesService, never()).saveArticleImages(any(ArticleImages.class));
    }

    @Test
    void testCreateArticlesFromContentfulArticleDto_WithImages() {
        // Mockowanie artykułów z obrazkami
        ContentfulArticleDto.Fields fields = mock(ContentfulArticleDto.Fields.class);
        when(fields.getEmployeeId()).thenReturn(1);
        when(fields.getHeadTitle()).thenReturn("Sample Title");
        when(fields.getShortDescription()).thenReturn("Sample Description");
        when(fields.getImportance()).thenReturn(5);
        when(fields.getHeadImgSrc()).thenReturn(new ContentfulArticleDto.Fields.Sys());
        when(fields.getSpecificTitle()).thenReturn("Specific Title");
        when(fields.getDescription()).thenReturn("Description");

        ContentfulArticleDto.Fields.Sys imgSys = new ContentfulArticleDto.Fields.Sys();
        imgSys.setId("img1");
        List<ContentfulArticleDto.Fields.Sys> imgSrcList = List.of(imgSys);
        when(fields.getImgSrcList()).thenReturn(imgSrcList);
        when(fields.getAltImgList()).thenReturn(List.of("Alt Image"));

        ContentfulArticleDto articleDto = new ContentfulArticleDto();
        articleDto.setFields(fields);

        List<ContentfulArticleDto> articleDtoList = List.of(articleDto);
        when(contentfulCreateArticleService.contentfulArticleDtoList()).thenReturn(articleDtoList);

        // Mockowanie pracownika
        Employee employee = mock(Employee.class);
        when(employeeService.findByEmployeeId(1L)).thenReturn(Optional.of(employee));

        // Testowanie
        contentfulCreateArticleService.createArticlesFromContentfulArticleDto();

        // Weryfikacja
        verify(articleInformationService, times(1)).saveArticle(any(ArticleInformation.class));
        verify(specificArticleService, times(1)).saveSpecificArticle(any(SpecificArticle.class));
        verify(articleImagesService, times(1)).saveArticleImages(any(ArticleImages.class));
    }
}