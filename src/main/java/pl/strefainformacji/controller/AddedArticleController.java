package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.ArticleInformationService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.List;

@Controller
@AllArgsConstructor
public class AddedArticleController {
    private final ArticleInformationService articleInformationService;
    private final SpecificArticleService specificArticleService;
    private final ArticleImagesService articleImagesService;

    @GetMapping("/add/viewAddedArticle")
    public String addedArticlePage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        ArticleInformation articleInformation = articleInformationService.findArticleInformation((Long) session.getAttribute("articleId"));
        SpecificArticle specificArticle = specificArticleService.findSpecificArticle((Long) session.getAttribute("specificArticleId"));
        List<ArticleImages> articleImages = articleImagesService.findArticleImages(specificArticle);

        model.addAttribute("articleInformation", articleInformation);
        model.addAttribute("specificArticle", specificArticle);
        model.addAttribute("articleImages", articleImages);

        session.removeAttribute("articleId");
        session.removeAttribute("specificArticleId");
        session.removeAttribute("numberOfImages");
        session.removeAttribute("articleImagesId");
        return "viewAddedArticle";
    }
}
