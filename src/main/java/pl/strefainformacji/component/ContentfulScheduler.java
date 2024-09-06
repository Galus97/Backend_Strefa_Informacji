package pl.strefainformacji.component;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.strefainformacji.service.ContentfulCreateArticleService;

@Component
@RequiredArgsConstructor
public class ContentfulScheduler {
    private final ContentfulCreateArticleService contentfulCreateArticleService;

    @Scheduled(fixedRate = 180000)
    public void fetchArticlesPeriodically(){
        contentfulCreateArticleService.createArticlesFromContentfulArticleDto();
    }
}
