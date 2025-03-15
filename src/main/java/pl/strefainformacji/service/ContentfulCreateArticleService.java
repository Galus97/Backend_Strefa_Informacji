package pl.strefainformacji.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.webclient.contentful.dto.ContentfulArticleDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
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
            ContentfulArticleDto contentfulArticleDto = contentfulService.getArticleById(entry);
            if (contentfulArticleDto != null) {
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

            Employee employee = employeeService.getEmployee((long) element.getFields().getEmployeeId());
            Employee generalEmployee = employeeService.getEmployee(1L);
            if (employee != null) {
                articleInformation.setEmployee(employee);
            } else {
                articleInformation.setEmployee(generalEmployee);
            }

            articleInformation.setContentfulId(element.getSys().getId());
            articleInformation.setImportance(element.getFields().getImportance());
            articleInformation.setTitle(element.getFields().getHeadTitle());
            articleInformation.setShortDescription(element.getFields().getShortDescription());
            articleInformation.setImgSrc(element.getFields().getHeadImgSrc().getId());
            articleInformation.setAltImg(element.getFields().getHeadAltImg());
            articleInformation.setLocalDateTime(LocalDateTime.now());

            articleInformationService.saveArticleInformation(articleInformation);

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

    private void importArticle(ContentfulArticleDto dto) {
        ArticleInformation articleInformation = mapToArticleInformation(dto);
        articleInformationService.saveArticleInformation(articleInformation);

        SpecificArticle specificArticle = mapToSpecificArticle(dto, articleInformation);
        specificArticleService.saveSpecificArticle(specificArticle);

        mapToArticleImages(dto, specificArticle)
                .forEach(articleImagesService::saveArticleImages);
    }

    private ArticleInformation mapToArticleInformation(ContentfulArticleDto dto) {
        ArticleInformation info = new ArticleInformation();
        info.setContentfulId(dto.getSys().getId());
        info.setImportance(dto.getFields().getImportance());
        info.setTitle(dto.getFields().getHeadTitle());
        info.setShortDescription(dto.getFields().getShortDescription());
        if (dto.getFields().getHeadImgSrc() != null) {
            info.setImgSrc(dto.getFields().getHeadImgSrc().getId());
        }
        info.setAltImg(dto.getFields().getHeadAltImg());
        info.setLocalDateTime(LocalDateTime.now());

        Long employeeId = dto.getFields().getEmployeeId() != null
                ? dto.getFields().getEmployeeId().longValue()
                : null;
        Employee employee = employeeService.getEmployee(employeeId);
        if (employee == null) {
            employee = employeeService.getEmployee(1L);
        }
        info.setEmployee(employee);
        return info;
    }

    private SpecificArticle mapToSpecificArticle(ContentfulArticleDto dto, ArticleInformation articleInformation) {
        SpecificArticle specificArticle = new SpecificArticle();
        specificArticle.setTitle(dto.getFields().getSpecificTitle());
        specificArticle.setDescription(dto.getFields().getDescription());
        specificArticle.setArticleInformation(articleInformation);
        return specificArticle;
    }

    private List<ArticleImages> mapToArticleImages(ContentfulArticleDto dto, SpecificArticle specificArticle) {
        List<ArticleImages> images = new ArrayList<>();
        List<ContentfulArticleDto.Fields.Sys> imgSrcList = dto.getFields().getImgSrcList();
        List<String> altImgList = dto.getFields().getAltImgList();
        if (imgSrcList != null && altImgList != null && imgSrcList.size() == altImgList.size()) {
            for (int i = 0; i < imgSrcList.size(); i++) {
                ArticleImages articleImages = new ArticleImages();
                articleImages.setSpecificArticle(specificArticle);
                articleImages.setImgSrc(imgSrcList.get(i).getId());
                articleImages.setAltImg(altImgList.get(i));
                images.add(articleImages);
            }
        }
        return images;
    }
}