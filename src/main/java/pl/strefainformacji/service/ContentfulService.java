package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.webclient.contentful.ContentfulClient;

@Service
@AllArgsConstructor
public class ContentfulService {

    private final ContentfulClient contentfulClient;

    public String getIdAndTitleOfArticle(){
        try {
            return contentfulClient.getIdAndTitleFromJson();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
