package pl.strefainformacji.webclient.contentful;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.strefainformacji.webclient.contentful.jsonLastAddedArticles.JsonFetcher;
import pl.strefainformacji.webclient.contentful.jsonLastAddedArticles.JsonIdExtractor;

import java.util.List;

@Component
public class ContentfulClient {
    @Value("${contentful.json}")
    private String contenfulKey;
    private RestTemplate restTemplate = new RestTemplate();

    public String getArticle() {
        String template = restTemplate.getForObject(contenfulKey, String.class);
        System.out.println(template);
        return template;
    }

    public List<String> getLastAddedArticles() throws Exception {

        List<String> list = JsonIdExtractor.extractIdsAndTitles(JsonFetcher.fetchJsonFromUrl(contenfulKey));
        return list;
    }
}
