package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.webclient.contentful.ContentfulClient;
import pl.strefainformacji.webclient.contentful.jsonArticles.dto.ContentfulArticleDto;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ContentfulService {

    private final ContentfulClient contentfulClient;
    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;
    private final ArticleImagesService articleImagesService;

    public List<String> addedArticleInContentful() {
        List<String> lastAddedArticles = new ArrayList<>();
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
        if(articleInformationService.findAllContentfulIds().isEmpty()){
            notAddedArticle = addedArticleInContentful();
        } else{
            for (String articleFromContentful : addedArticleInContentful()) {
                for (String contentfulIdFromDatabase : articleInformationService.findAllContentfulIds()) {
                    if (!contentfulIdFromDatabase.equals(articleFromContentful)) {
                        notAddedArticle.add(articleFromContentful);

                    }
                }
            }
        }

        return notAddedArticle;
    }

    public List<ContentfulArticleDto> createContentfulArticleDtoFromJsonContentful (){
        List<ContentfulArticleDto> listOfContentfulArticleDto = new ArrayList<>();
        List<String> listOfArticlesIdToAdd = articleToAddToDatabase();

        for (String entry : listOfArticlesIdToAdd) {
            ContentfulArticleDto jsonFieldsValue = contentfulClient.getJsonFieldsValue(entry);
            listOfContentfulArticleDto.add(jsonFieldsValue);
        }
        return listOfContentfulArticleDto;
    }

    public void createArticlesFromContentfulArticleDto(Employee employee){
        List<ContentfulArticleDto> contentfulArticleDtos = createContentfulArticleDtoFromJsonContentful();

        for(ContentfulArticleDto element : contentfulArticleDtos){
            ArticleInformation articleInformation = new ArticleInformation();
            SpecificArticle specificArticle = new SpecificArticle();
            ArticleImages articleImages = new ArticleImages();

            articleInformation.setEmployee(employee);
            articleInformation.setContentfulId(element.getSys().getId());
            articleInformation.setImportance(element.getFields().getImportance());
            articleInformation.setTitle(element.getFields().getHeadTitle());
            articleInformation.setShortDescription(element.getFields().getShortDescription());
            articleInformation.setImgSrc(element.getFields().getHeadImgSrc().getId());
            articleInformation.setAltImg(element.getFields().getHeadAltImg());

            articleInformationService.saveArticle(articleInformation);

            specificArticle.setTitle(element.getFields().getSpecificTitle());
            specificArticle.setDescription(element.getFields().getDescription());
            specificArticle.setArticleInformation(articleInformation);

            specificArticleService.saveSpecificArticle(specificArticle);

            for(ContentfulArticleDto.Fields.Sys img : element.getFields().getImgSrcList()){
                articleImages.setSpecificArticle(specificArticle);
                articleImages.setImgSrc(img.getId());
                articleImagesService.saveArticleImages(articleImages);

            }

        }
    }
}
