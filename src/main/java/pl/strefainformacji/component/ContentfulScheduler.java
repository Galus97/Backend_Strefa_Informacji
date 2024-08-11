package pl.strefainformacji.component;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.strefainformacji.service.ContentfulService;

@Component
@RequiredArgsConstructor
public class ContentfulScheduler {
    private final ContentfulService contentfulService;

    @Scheduled(fixedRate = 180000)
    public void fetchArticlesPeriodically(){
        contentfulService.createArticlesFromContentfulArticleDto();
    }
}
