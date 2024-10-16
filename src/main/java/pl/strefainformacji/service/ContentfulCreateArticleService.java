package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.webclient.contentful.dto.ContentfulArticleDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContentfulCreateArticleService {

    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;
    private final ArticleImagesService articleImagesService;
    private final EmployeeService employeeService;
    private final ContentfulService contentfulService;

    public List<String> articleToAddToDatabase() {
        List<String> notAddedArticle = new ArrayList<>();
        if (articleInformationService.findAllContentfulIds().isEmpty()) {
            notAddedArticle = contentfulService.getAllArticlesIds();
        } else {
            for (String contentfulArticleId : contentfulService.getAllArticlesIds()) {
                if (isArticleExistInDatabase(contentfulArticleId, articleInformationService.findAllContentfulIds())) {
                    notAddedArticle.add(contentfulArticleId);
                }
            }
        }
        return notAddedArticle;
    }

    private boolean isArticleExistInDatabase(String contentfulArticleId, List<String> database) {
        for (String databaseElement : database) {
            if (contentfulArticleId.equals(databaseElement)) {
                return false;
            }
        }
        return true;
    }

    public List<ContentfulArticleDto> contentfulArticleDtoList() {
        List<ContentfulArticleDto> listOfContentfulArticleDto = new ArrayList<>();
        List<String> listOfArticlesIdToAdd = articleToAddToDatabase();

        for (String entry : listOfArticlesIdToAdd) {
            if (contentfulService.getArticleById(entry) != null) {
                ContentfulArticleDto contentfulArticleDto = contentfulService.getArticleById(entry);
                listOfContentfulArticleDto.add(contentfulArticleDto);
            }
        }
        return listOfContentfulArticleDto;
    }

    public void createArticlesFromContentfulArticleDto() {
        List<ContentfulArticleDto> contentfulArticleDtos = contentfulArticleDtoList();

        for (ContentfulArticleDto element : contentfulArticleDtos) {
            ArticleInformation articleInformation = new ArticleInformation();
            SpecificArticle specificArticle = new SpecificArticle();

            Optional<Employee> employee = employeeService.findByEmployeeId((long) element.getFields().getEmployeeId());
            Optional<Employee> generalEmployee = employeeService.findByEmployeeId(1L);
            if (employee.isPresent()) {
                articleInformation.setEmployee(employee.get());
            } else generalEmployee.ifPresent(articleInformation::setEmployee);

            articleInformation.setContentfulId(element.getSys().getId());
            articleInformation.setImportance(element.getFields().getImportance());
            articleInformation.setTitle(element.getFields().getHeadTitle());
            articleInformation.setShortDescription(element.getFields().getShortDescription());
            articleInformation.setImgSrc(element.getFields().getHeadImgSrc().getId());
            articleInformation.setAltImg(element.getFields().getHeadAltImg());
            articleInformation.setLocalDateTime(LocalDateTime.now());

            articleInformationService.saveArticle(articleInformation);

            specificArticle.setTitle(element.getFields().getSpecificTitle());
            specificArticle.setDescription(element.getFields().getDescription());
            specificArticle.setArticleInformation(articleInformation);

            specificArticleService.saveSpecificArticle(specificArticle);

            for (int i = 0; i < element.getFields().getImgSrcList().size(); i++) {
                ArticleImages articleImages = new ArticleImages();
                articleImages.setSpecificArticle(specificArticle);
                articleImages.setImgSrc(element.getFields().getImgSrcList().get(i).getId());
                articleImages.setAltImg(element.getFields().getAltImgList().get(i));
                articleImagesService.saveArticleImages(articleImages);
            }
        }
    }
}