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

import java.lang.reflect.Field;
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
    public String saveWholeArticle(@AuthenticationPrincipal CurrentEmployee currentEmployee, Model model, HttpServletRequest request) {

        ArticleInformation articleInformation = articleInformationFormController.articleInformation;
        articleInformation.setContentfulId("00000");
        SpecificArticle specificArticle = specificArticleFormController.specificArticle;
        List<ArticleImages> articleImagesList = articleImagesFormController.articleImagesList;

        setForeignKey(currentEmployee, articleInformation, specificArticle, articleImagesList);

        if (isEveryFieldsExist(articleInformation, specificArticle, articleImagesList)) {
            saveArticleToDatabase(articleInformation, specificArticle, articleImagesList);
        }
        addModelAttribute(model, articleInformation, specificArticle, articleImagesList);

        HttpSession session = request.getSession();
        if (session.getAttribute("Article") != null && "articleImages".equals(session.getAttribute("Article"))) {
            session.invalidate();
            return "article";
        } else {
            return "redirect:/panel";
        }
    }


    private void setForeignKey(CurrentEmployee currentEmployee, ArticleInformation articleInformation, SpecificArticle specificArticle, List<ArticleImages> articleImagesList) {
        articleInformation.setEmployee(currentEmployee.getEmployee());
        specificArticle.setArticleInformation(articleInformationFormController.articleInformation);
        for (ArticleImages articleImages : articleImagesList) {
            articleImages.setSpecificArticle(specificArticleFormController.specificArticle);
        }
    }

    private void saveArticleToDatabase(ArticleInformation articleInformation, SpecificArticle specificArticle, List<ArticleImages> articleImagesList) {
        articleInformationService.saveArticleInformation(articleInformation);
        specificArticleService.saveSpecificArticle(specificArticle);
        for (ArticleImages articleImages : articleImagesList) {
            articleImagesService.saveArticleImages(articleImages);
        }
    }

    private void addModelAttribute(Model model, ArticleInformation articleInformation, SpecificArticle specificArticle, List<ArticleImages> articleImagesList) {
        model.addAttribute("articleInformation", articleInformation);
        model.addAttribute("specificArticle", specificArticle);
        model.addAttribute("articleImages", articleImagesList);
    }

    private boolean isEveryFieldsExist(ArticleInformation articleInformation, SpecificArticle specificArticle, List<ArticleImages> articleImages) {
        if (!areAllFieldsNonNull(articleInformation) || !areAllFieldsNonNull(specificArticle)) {
            return false;
        }

        for (ArticleImages articleImage : articleImages) {
            if (!areAllFieldsNonNull(articleImage)) {
                return false;
            }
        }

        return true;
    }

    private boolean areAllFieldsNonNull(Object object) {
        if (object == null) {
            return false;
        }

        for (Field field : object.getClass().getFields()) {
            field.setAccessible(true);
            try {
                if (field.get(object) == null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Unable to access field: " + field.getName(), e);
            }
        }
        return true;
    }
}