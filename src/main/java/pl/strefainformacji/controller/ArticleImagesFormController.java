package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public String articleImagesForm(){
        return "articleImages";
    }

    @PostMapping("/add/articleImages")
    public String saveArticleImagesFromForm(HttpServletRequest request, @RequestParam String src1){
        HttpSession session = request.getSession();
        Long specificArticleId = (Long)session.getAttribute("specificArticleId");

        for(int i = 1; i <= 12; i += 2){
            String srcImg = request.getParameter(String.valueOf(i));
            String altImg = request.getParameter(String.valueOf(i + 1));
            if(srcImg != null && altImg != null){
                ArticleImages articleImages = new ArticleImages();
                articleImages.setSpecificArticle(specificArticleService.findSpecificArticle(specificArticleId));
                articleImages.setImgSrc(srcImg);
                articleImages.setAltImg(altImg);
                articleImagesService.saveArticleImages(articleImages);
                System.out.println("------------------------" + articleImages.getArticleImagesId());
            }
       }
        return "redirect:viewAddedArticle";
    }
}