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
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContentfulService {

    private final ContentfulClient contentfulClient;
    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;
    private final ArticleImagesService articleImagesService;
    private final EmployeeService employeeService;

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
            for(String contentfulArticleId : addedArticleInContentful()){
                if(isArticleExistInDatabase(contentfulArticleId, articleInformationService.findAllContentfulIds())){
                    notAddedArticle.add(contentfulArticleId);
                }
            }
        }
        return notAddedArticle;
    }

    private boolean isArticleExistInDatabase (String contentfulArticleId, List<String> database){
            for(String databaseElement : database){
                if(contentfulArticleId.equals(databaseElement)){
                    return false;
                }
            }
        return true;
    }

    public List<ContentfulArticleDto> contentfulArticleDtoList(){
        List<ContentfulArticleDto> listOfContentfulArticleDto = new ArrayList<>();
        List<String> listOfArticlesIdToAdd = articleToAddToDatabase();

        for (String entry : listOfArticlesIdToAdd) {
            ContentfulArticleDto jsonFieldsValue = null;
            try {
                if(contentfulClient.getJsonFieldsValue(entry) != null){
                    jsonFieldsValue = contentfulClient.getJsonFieldsValue(entry);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            listOfContentfulArticleDto.add(jsonFieldsValue);
        }
        return listOfContentfulArticleDto;
    }

    public void createArticlesFromContentfulArticleDto(){
        List<ContentfulArticleDto> contentfulArticleDtos = contentfulArticleDtoList();

        for(ContentfulArticleDto element : contentfulArticleDtos){
            ArticleInformation articleInformation = new ArticleInformation();
            SpecificArticle specificArticle = new SpecificArticle();
            ArticleImages articleImages = new ArticleImages();

            Optional<Employee> employee = employeeService.findByEmployeeId((long)element.getFields().getEmployeeId());
            Optional<Employee> generalEmployee = employeeService.findByEmployeeId(1L);
            if(employee.isPresent()){
                articleInformation.setEmployee(employee.get());
            } else generalEmployee.ifPresent(articleInformation::setEmployee);

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