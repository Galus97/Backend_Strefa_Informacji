package pl.strefainformacji.webclient.contentful;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.strefainformacji.webclient.contentful.jsonArticles.JsonFetcher;
import pl.strefainformacji.webclient.contentful.jsonArticles.JsonExtractor;
import pl.strefainformacji.webclient.contentful.jsonArticles.dto.ContentfulArticleDto;

import java.util.List;

@Component
public class ContentfulClient {
    @Value("${contentful.json}")
    private String contenfulKey;

    @Value("${contentful.access.token}")
    private String accesToken;

    @Value("${contentful.entry}")
    private String entry;

    public List<String> getLastAddedArticles() throws Exception {
        return JsonExtractor.extractIdsAndTitles(JsonFetcher.fetchJsonFromUrl(contenfulKey));
    }

    public ContentfulArticleDto getJsonFieldsValue (String entryId) throws Exception{
            return JsonExtractor.getFildsFromJson(JsonFetcher.fetchJsonFromUrl(entry + entryId + accesToken));
    }
}
