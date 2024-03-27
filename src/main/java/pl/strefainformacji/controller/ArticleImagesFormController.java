package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.Objects;

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
    public String saveArticleImagesFromForm(HttpServletRequest request){
        HttpSession session = request.getSession();
        Long specificArticleId = (Long)session.getAttribute("specificArticleId");


       for(int i = 1; i <= 6; i++){
           String parameterSrc = request.getParameter("ingSrc" + i);
           String parameterAlt = request.getParameter("altImg" + i);
           if(Objects.nonNull(parameterSrc) && Objects.nonNull(parameterAlt)){
               ArticleImages articleImages = new ArticleImages();
               articleImages.setSpecificArticle(specificArticleService.findSpecificArticle(specificArticleId));
               articleImages.setImgSrc(parameterSrc);
               articleImages.setAltImg(parameterAlt);

               articleImagesService.saveArticleImages(articleImages);
               session.setAttribute("articleImagesId", articleImages.getArticleImagesId());
               System.out.println(articleImages.getArticleImagesId());
           }
       }
        return "redirect:viewAddedArticle";
    }
}
