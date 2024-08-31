package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SaveArticleController {
    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;
    private final ArticleImagesService articleImagesService;
    private final ArticleInformationFormController articleInformationFormController;
    private final SpecificArticleFormController specificArticleFormController;
    private final ArticleImagesFormController articleImagesFormController;

    @GetMapping("/article")
    public String getArticle(@AuthenticationPrincipal CurrentEmployee curentEmployee, Model model, HttpServletRequest request) {

        ArticleInformation articleInformation = articleInformationFormController.articleInformation;
        SpecificArticle specificArticle = specificArticleFormController.specificArticle;
        List<ArticleImages> articleImagesList = articleImagesFormController.articleImagesList;

        articleInformation.setEmployee(curentEmployee.getEmployee());
        articleInformation.setContentfulId("00000");
        specificArticle.setArticleInformation(articleInformationFormController.articleInformation);
        for (ArticleImages articleImages : articleImagesList) {
            articleImages.setSpecificArticle(specificArticleFormController.specificArticle);
        }

        if (isEveryFieldsExist(articleInformation, specificArticle, articleImagesList)) {
            articleInformationService.saveArticle(articleInformation);
            specificArticleService.saveSpecificArticle(specificArticle);
            for (ArticleImages articleImages : articleImagesList) {
                articleImagesService.saveArticleImages(articleImages);
            }
        }
        model.addAttribute("articleInformation", articleInformation);
        model.addAttribute("specificArticle", specificArticle);
        model.addAttribute("articleImages", articleImagesList);

        HttpSession session = request.getSession();
        if(session.getAttribute("Article") != null && "articleImages".equals(session.getAttribute("Article"))){
            session.invalidate();
            return "article";
        } else {
            return "redirect:/panel";
        }
    }

    private boolean isEveryFieldsExist(ArticleInformation articleInformation, SpecificArticle specificArticle, List<ArticleImages> articleImages) {

        if (articleInformation.getContentfulId() == null || articleInformation.getTitle() == null || articleInformation.getShortDescription() == null ||
                articleInformation.getImportance() == null || articleInformation.getImgSrc() == null || articleInformation.getAltImg() == null || articleInformation.getEmployee() == null) {
            return false;
        }

        if (specificArticle.getTitle() == null || specificArticle.getDescription() == null || specificArticle.getArticleInformation() == null) {
            return false;
        }

        for (ArticleImages articleImage : articleImages) {
            if (articleImage.getImgSrc() == null || articleImage.getAltImg() == null || articleImage.getSpecificArticle() == null) {
                return false;
            }
        }

        return true;
    }
}