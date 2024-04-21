package pl.strefainformacji.controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.SpecificArticleService;

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
        Long specificArticleId = Long.parseLong(request.getParameter("specificArticleId"));
        for(int i = 1; i <= 10; i++){
            String imgSrc = request.getParameter("imgSrc" + i);
            String altImg = request.getParameter("altImg" + i);
            if(!imgSrc.isEmpty() && !altImg.isEmpty()){
                ArticleImages articleImages = new ArticleImages();
                articleImages.setImgSrc(imgSrc);
                articleImages.setAltImg(altImg);
                articleImages.setSpecificArticle(specificArticleService.getSpecificArticleByArticleInformationId(specificArticleId));
                articleImagesService.saveArticleImages(articleImages);
            }
        }
        return "redirect:viewAddedArticle?specificArticleId=" + specificArticleId;
    }
}
