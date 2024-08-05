package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.webclient.contentful.ContentfulClient;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ContentfulService {

    private final ContentfulClient contentfulClient;
    private final ArticleInformationService articleInformationService;

    public List<String> checkExistArticle(){
        try {
            return contentfulClient.getLastAddedArticles();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> articleToAddToDatabase(){
        List<String> notAddedArticle = new ArrayList<>();
        for (String articleFromContentful : checkExistArticle()) {
            for(String contentfulIdFromDatabase : articleInformationService.findAllContentfulIds()){
                if(!contentfulIdFromDatabase.equals(articleFromContentful)){
                    notAddedArticle.add(articleFromContentful);
                }
            }
        }
        return notAddedArticle;
    }
}
