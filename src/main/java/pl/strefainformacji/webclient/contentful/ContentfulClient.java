package pl.strefainformacji.webclient.contentful;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.strefainformacji.model.ContentfulDto;
import pl.strefainformacji.webclient.contentful.jsonArticles.JsonFetcher;
import pl.strefainformacji.webclient.contentful.jsonArticles.JsonIdExtractor;
import pl.strefainformacji.webclient.contentful.jsonArticles.dto.ContentfulArticleDto;

import java.util.List;

@Component
public class ContentfulClient {
    @Value("${contentful.json}")
    private String contenfulKey;

    @Value("${contentful.access.token}")
    private String accesToken;

    @Value("${contentful.entrie}")
    private String entrie;

    private RestTemplate restTemplate = new RestTemplate();

    public ContentfulDto getArticleFromContentful(String entreId) {
        ContentfulArticleDto contentfulArticleDto = restTemplate.getForObject(entrie + entreId + accesToken,
                ContentfulArticleDto.class);

        System.out.println("--------------------------------------------------------------------------");
        System.out.println(contentfulArticleDto.getSysId());
        System.out.println(contentfulArticleDto.getFieldsHeadTitle());
        System.out.println(contentfulArticleDto.getFieldsShortDescription());
        System.out.println(contentfulArticleDto.getFieldsImportance());
        System.out.println(contentfulArticleDto.getFieldsHeadAltImg());
        System.out.println(contentfulArticleDto.getFieldsSpecificTitle());
        System.out.println(contentfulArticleDto.getFieldsDescription());
        System.out.println("--------------------------------------------------------------------------");

        return ContentfulDto.builder()
                .sysId(contentfulArticleDto.getSysId())
                .fieldsHeadTitle(contentfulArticleDto.getFieldsHeadTitle())
                .fieldsShortDescription(contentfulArticleDto.getFieldsShortDescription())
                .fieldsImportance(contentfulArticleDto.getFieldsImportance())
                .fieldsHeadAltImg(contentfulArticleDto.getFieldsHeadAltImg())
                .fieldsSpecificTitle(contentfulArticleDto.getFieldsSpecificTitle())
                .fieldsDescription(contentfulArticleDto.getFieldsDescription())
                .build();
    }

    public List<String> getLastAddedArticles() throws Exception {
        return JsonIdExtractor.extractIdsAndTitles(JsonFetcher.fetchJsonFromUrl(contenfulKey));
    }
}
