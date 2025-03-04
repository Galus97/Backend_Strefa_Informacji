package pl.strefainformacji.component;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.strefainformacji.service.ContentfulCreateArticleService;

/**
 * A scheduled Spring component responsible for fetching and processing articles from Contentful.
 */
@Component
@RequiredArgsConstructor
public class ContentfulScheduler {
    private final ContentfulCreateArticleService contentfulCreateArticleService;

    /**
     * Periodically fetches articles from Contentful every 3 minutes (180,000 ms) and processes them.
     */
    @Scheduled(fixedRate = 180000)
    public void fetchArticlesPeriodically() {
        contentfulCreateArticleService.createArticlesFromContentfulArticleDto();
    }
}