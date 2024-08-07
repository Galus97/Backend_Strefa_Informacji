package pl.strefainformacji.webclient.contentful;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.strefainformacji.model.ContentfulDto;
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

    private RestTemplate restTemplate = new RestTemplate();

//    public ContentfulDto getArticleFromContentful(String entreId) {
//        ContentfulArticleDto contentfulArticleDto = restTemplate.getForObject(entry + entreId + accesToken,
//                ContentfulArticleDto.class);
//
//        return ContentfulDto.builder()
//                .build();
//    }

    public List<String> getLastAddedArticles() throws Exception {
        return JsonExtractor.extractIdsAndTitles(JsonFetcher.fetchJsonFromUrl(contenfulKey));
    }

    public ContentfulArticleDto getJsonFieldsValue (String entreId) throws Exception {
        try{
            System.out.println(JsonFetcher.fetchJsonFromUrl(entry + entreId + accesToken));

             return JsonExtractor.getFildsFromJson(JsonFetcher.fetchJsonFromUrl(entry + entreId + accesToken));
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
