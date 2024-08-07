package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.model.ContentfulDto;
import pl.strefainformacji.repository.ArticleInformationRepository;
import pl.strefainformacji.webclient.contentful.ContentfulClient;
import pl.strefainformacji.webclient.contentful.jsonArticles.dto.ContentfulArticleDto;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ContentfulService {

    private final ContentfulClient contentfulClient;
    private final ArticleInformationService articleInformationService;
    private final ArticleInformationRepository articleInformationRepository;

    public List<String> addedArticleInContentful() {
        List<String> lastAddedArticles = null;
        try {
            lastAddedArticles = contentfulClient.getLastAddedArticles();
            return lastAddedArticles;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastAddedArticles;
    }

    public List<String> articleToAddToDatabase() {
        List<String> notAddedArticle = new ArrayList<>();
        for (String articleFromContentful : addedArticleInContentful()) {
            for (String contentfulIdFromDatabase : articleInformationService.findAllContentfulIds()) {
                if (!contentfulIdFromDatabase.equals(articleFromContentful)) {
                    notAddedArticle.add(articleFromContentful);
                }
            }
        }
        return notAddedArticle;
    }

    public String test (){
        StringBuilder sb = new StringBuilder();
        List<String> listOfArticlesIdToAdd = articleToAddToDatabase();
        for (String entry : listOfArticlesIdToAdd) {
            try {
                sb.append(contentfulClient.getJsonFieldsValue(entry));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return sb.toString();
    }

//    public List<ContentfulDto> addArticleToDatabase(){
//        List<ContentfulDto> contentfulDtos = new ArrayList<>();
//        List<String> listOfArticlesIdToAdd = articleToAddToDatabase();
//        for (String entry : listOfArticlesIdToAdd) {
//            contentfulDtos.add(contentfulClient.getJsonFieldsValue(entry));
//        }
//        return contentfulDtos;
//    }
//
//    public void saveArticlesFromContentful(Employee employee){
//        ArticleInformation articleInformation = new ArticleInformation();
//        List<ContentfulDto> contentfulDtos = addArticleToDatabase();
//        for (ContentfulDto contentfulDto : contentfulDtos) {
//            articleInformation.setContentfulId(contentfulDto.getSysId());
//            articleInformation.setTitle(contentfulDto.getFieldsHeadTitle());
//            articleInformation.setShortDescription(contentfulDto.getFieldsShortDescription());
//            articleInformation.setImportance(contentfulDto.getFieldsImportance());
//            articleInformation.setAltImg(contentfulDto.getFieldsHeadAltImg());
//            articleInformation.setEmployee(employee);
//
//            articleInformationRepository.save(articleInformation);
//        }
//}
}
