package pl.strefainformacji.service;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.FetchQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.exception.ContentfulIntegrationException;
import pl.strefainformacji.webclient.contentful.ContentfulClient;
import pl.strefainformacji.webclient.contentful.dto.ContentfulArticleDto;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ContentfulServiceTest {

    @Mock
    private ContentfulClient contentfulClient;

    @Mock
    private CDAClient cdaClient;

    @Mock
    private MessageService messageService;

    private ContentfulService contentfulService;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(contentfulClient.createClient()).thenReturn(cdaClient);
        contentfulService = new ContentfulService(contentfulClient, messageService);
    }

    // Testy, w których ręcznie tworzymy mocka dla FetchQuery, jak poniżej:
    @Test
    void getAllArticlesIds_ShouldReturnListOfIds() {
        @SuppressWarnings("unchecked")
        FetchQuery<CDAEntry> fetchQuery = mock(FetchQuery.class);
        when(cdaClient.fetch(CDAEntry.class)).thenReturn(fetchQuery);
        when(fetchQuery.withContentType("article")).thenReturn(fetchQuery);
        when(fetchQuery.orderBy("-sys.createdAt")).thenReturn(fetchQuery);
        when(fetchQuery.limit(10)).thenReturn(fetchQuery);

        CDAArray cdaArray = mock(CDAArray.class);
        CDAEntry entry = mock(CDAEntry.class);
        when(entry.id()).thenReturn("article1");
        when(cdaArray.items()).thenReturn(Collections.singletonList(entry));
        when(fetchQuery.all()).thenReturn(cdaArray);

        List<String> result = contentfulService.getAllArticlesIds();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("article1", result.get(0));
    }

    @Test
    void getArticleById_ShouldReturnArticleDto() {
        String articleId = "article1";
        @SuppressWarnings("unchecked")
        FetchQuery<CDAEntry> fetchQuery = mock(FetchQuery.class);
        when(cdaClient.fetch(CDAEntry.class)).thenReturn(fetchQuery);

        CDAEntry entry = mock(CDAEntry.class);
        when(entry.id()).thenReturn(articleId);
        when(entry.getField("headTitle")).thenReturn("Test Title");
        when(entry.getField("shortDescription")).thenReturn("Test Description");
        when(entry.getField("importance")).thenReturn(1.0);
        when(entry.getField("employeeId")).thenReturn(2.0);
        when(fetchQuery.one(articleId)).thenReturn(entry);

        ContentfulArticleDto result = contentfulService.getArticleById(articleId);

        assertNotNull(result);
        assertEquals(articleId, result.getSys().getId());
        assertEquals("Test Title", result.getFields().getHeadTitle());
        assertEquals("Test Description", result.getFields().getShortDescription());
        assertEquals(1, result.getFields().getImportance());
    }

    @Test
    void getArticleById_ShouldThrowException_WhenErrorOccurs() {
        String articleId = "invalidId";
        @SuppressWarnings("unchecked")
        FetchQuery<CDAEntry> fetchQuery = mock(FetchQuery.class);
        when(cdaClient.fetch(CDAEntry.class)).thenReturn(fetchQuery);
        when(fetchQuery.one(articleId)).thenThrow(new RuntimeException("Error"));
        when(messageService.getMessage("error.contentfull", articleId))
                .thenReturn("Error fetching content");

        ContentfulIntegrationException exception = assertThrows(ContentfulIntegrationException.class,
                () -> contentfulService.getArticleById(articleId));

        assertEquals("Error fetching content", exception.getMessage());
    }
}
