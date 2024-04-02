package pl.strefainformacji.controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ArticleImagesFormController {
    private final ArticleImagesService articleImagesService;
    private final SpecificArticleService specificArticleService;
    @GetMapping("/add/articleImages")
    public String articleImagesForm(@RequestParam(required = false)Long specificArticleId, HttpServletRequest request){
        request.setAttribute("specificArticleId", specificArticleId);
        return "articleImages";
    }
    @PostMapping("/add/articleImages")
    public String saveArticleImagesFromForm(HttpServletRequest request){

        for(int i = 1; i <= 2; i++){
            Long specificArticleId = Long.parseLong(request.getParameter("specificArticleId"));
            ArticleImages articleImages = new ArticleImages();
            articleImages.setImgSrc(request.getParameter("imgSrc" + i));
            articleImages.setAltImg(request.getParameter("altImg" + i));
            articleImages.setSpecificArticle(specificArticleService.findSpecificArticle(specificArticleId));
            articleImagesService.saveArticleImages(articleImages);
        }
        return "redirect:viewAddedArticle";
    }
}
