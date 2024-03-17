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

@Controller
@AllArgsConstructor
public class ArticleImagesFormController {

    private final ArticleImagesService articleImagesService;
    private final SpecificArticleService specificArticleService;

    @GetMapping("/add/articleImages")
    public String articleImagesForm(Model model){
        model.addAttribute("articleImages", new ArticleImages());
        return "articleImages";
    }

    @PostMapping("/add/articleImages")
    public String saveArticleImagesFromForm(@Valid ArticleImages articleImages, BindingResult bindingResult, HttpServletRequest request){
        HttpSession session = request.getSession();
        Long specificArticleId = (Long)session.getAttribute("specificArticleId");
        int numberOfImages = Integer.parseInt(String.valueOf(session.getAttribute("numberOfImages")));

        if(bindingResult.hasErrors()){
            return "badBindingImages";
        }
        if(numberOfImages != 0){
            session.setAttribute("numberOfImages", numberOfImages - 1);
            articleImages.setSpecificArticle(specificArticleService.findSpecificArticle(specificArticleId));
            session.setAttribute("articleImagesId", articleImages.getArticleImagesId());
            articleImagesService.saveArticleImages(articleImages);
            if (numberOfImages != 1) {
                return "redirect:articleImages";
            }
        }


        return "redirect:viewAddedArticle";
    }
}
