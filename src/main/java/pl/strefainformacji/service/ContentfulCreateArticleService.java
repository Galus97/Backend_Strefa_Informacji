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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentfulCreateArticleService {

    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;
    private final ArticleImagesService articleImagesService;
    private final EmployeeService employeeService;
    private final ContentfulService contentfulService;

    public void importNewArticles() {
        List<String> existingContentfulIds = articleInformationService.findAllContentfulIds();
        List<String> allContentfulIds = contentfulService.getAllArticlesIds();

        List<String> newArticleIds = allContentfulIds.stream()
                .filter(id -> !existingContentfulIds.contains(id))
                .collect(Collectors.toList());

        List<ContentfulArticleDto> newArticles = newArticleIds.stream()
                .map(contentfulService::getArticleById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        newArticles.forEach(this::importArticle);
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