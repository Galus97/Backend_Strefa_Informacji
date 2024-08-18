package pl.strefainformacji.controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.service.ArticleImagesService;
import pl.strefainformacji.service.EmployeeService;
import pl.strefainformacji.service.SpecificArticleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ArticleImagesFormController {
    private final ArticleImagesService articleImagesService;
    private final SpecificArticleService specificArticleService;
    private final EmployeeService employeeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleImagesFormController.class);

    public List<ArticleImages> articleImagesList;

    @GetMapping("/add/articleImages")
    public String articleImagesForm(@AuthenticationPrincipal CurrentEmployee curentEmployee){
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            return "articleImages";
        } else {
            return "redirect:/verifyEmail";
        }

    }
    @PostMapping("/add/articleImages")
    public String saveArticleImagesFromForm(@RequestParam Map<String, String> allParams){

        articleImagesList = new ArrayList<>();
        for(int i = 1; i <= 10; i++){
            String imgSrc = allParams.get("imgSrc" + i);
            String altImg = allParams.get("altImg" + i);

            if(imgSrc != null && altImg != null && !imgSrc.isEmpty() && !altImg.isEmpty()){
                ArticleImages articleImages = new ArticleImages();
                articleImages.setImgSrc(imgSrc);
                articleImages.setAltImg(altImg);
                articleImagesList.add(articleImages);
                //articleImagesService.saveArticleImages(articleImages);
            }
        }
        //return "redirect:viewAddedArticle?specificArticleId=" + specificArticleId;
        return "redirect:/article";
    }
}