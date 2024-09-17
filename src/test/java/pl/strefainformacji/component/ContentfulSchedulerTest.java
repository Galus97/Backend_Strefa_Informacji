package pl.strefainformacji.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Scheduled;
import pl.strefainformacji.service.ContentfulCreateArticleService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContentfulSchedulerTest {

    private ContentfulCreateArticleService contentfulCreateArticleService;
    private ContentfulScheduler contentfulScheduler;

    @BeforeEach
    void setUp() {
        contentfulCreateArticleService = mock(ContentfulCreateArticleService.class);
        contentfulScheduler = new ContentfulScheduler(contentfulCreateArticleService);
    }

    @Test
    void testFetchArticlesPeriodically() {
        contentfulScheduler.fetchArticlesPeriodically();

        verify(contentfulCreateArticleService, times(1)).createArticlesFromContentfulArticleDto();
    }

    @Test
    void testScheduledAnnotationPresent() throws NoSuchMethodException {
        Scheduled scheduled = ContentfulScheduler.class.getMethod("fetchArticlesPeriodically")
                .getAnnotation(Scheduled.class);

        assertNotNull(scheduled, "@Scheduled annotation should be present on fetchArticlesPeriodically method");
        assertEquals(180000, scheduled.fixedRate(), "Fixed rate for @Scheduled should be 180000 ms (3 minutes)");
    }
}