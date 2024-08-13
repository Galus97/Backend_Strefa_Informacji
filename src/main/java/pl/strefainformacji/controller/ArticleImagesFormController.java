package pl.strefainformacji.controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
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

import java.util.Map;

@Controller
@AllArgsConstructor
public class ArticleImagesFormController {
    private final ArticleImagesService articleImagesService;
    private final SpecificArticleService specificArticleService;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(ArticleImagesFormController.class);

    @GetMapping("/add/articleImages")
    public String articleImagesForm(@RequestParam(required = false)Long specificArticleId, HttpServletRequest request, @AuthenticationPrincipal CurrentEmployee curentEmployee){
        request.setAttribute("specificArticleId", specificArticleId);
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            return "articleImages";
        } else {
            return "redirect:verifyEmail";
        }

    }
    @PostMapping("/add/articleImages")
    public String saveArticleImagesFromForm(HttpServletRequest request, @RequestParam Map<String, String> allParams){
        String specificArticleIdStr = request.getParameter("specificArticleId");
        if (specificArticleIdStr == null) {
            logger.error("Specific article ID is null");
            return "badBindingImages";
        }
        Long specificArticleId;

        try{
            specificArticleId = Long.parseLong(specificArticleIdStr);
        } catch (NumberFormatException e){
            logger.error("Specific article ID is invalid: " + specificArticleIdStr);
            return "badBindingImages";
        }


        SpecificArticle specificArticleByArticleInformationId = specificArticleService.getSpecificArticle(specificArticleId);
        logger.info("Saving article images from form");

        if(specificArticleByArticleInformationId == null){
            logger.error("Specific article ID is null");
            return "badBindingImages";
        }

        for(int i = 1; i <= 10; i++){
            String imgSrc = allParams.get("imgSrc" + i);
            String altImg = allParams.get("altImg" + i);

            if(imgSrc != null && altImg != null && !imgSrc.isEmpty() && !altImg.isEmpty()){
                ArticleImages articleImages = new ArticleImages();
                articleImages.setImgSrc(imgSrc);
                articleImages.setAltImg(altImg);
                articleImages.setSpecificArticle(specificArticleByArticleInformationId);
                articleImagesService.saveArticleImages(articleImages);
            }
        }
        return "redirect:viewAddedArticle?specificArticleId=" + specificArticleId;
    }
}