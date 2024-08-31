package pl.strefainformacji.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ArticleImagesFormController {
    private final EmployeeService employeeService;

    public List<ArticleImages> articleImagesList;

    @GetMapping("/add/articleImages")
    public String articleImagesForm(@AuthenticationPrincipal CurrentEmployee curentEmployee) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            return "articleImages";
        } else {
            return "redirect:/verifyEmail";
        }

    }

    @PostMapping("/add/articleImages")
    public String saveArticleImagesFromForm(@RequestParam Map<String, String> allParams, Model model) {

        articleImagesList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String imgSrc = allParams.get("imgSrc" + i);
            String altImg = allParams.get("altImg" + i);

            if (imgSrc != null && altImg != null && !imgSrc.isEmpty() && !altImg.isEmpty()) {
                ArticleImages articleImages = new ArticleImages();
                articleImages.setImgSrc(imgSrc);
                articleImages.setAltImg(altImg);
                articleImagesList.add(articleImages);
            }
        }

        if(articleImagesList.isEmpty()){
            model.addAttribute("errorImage", "Musisz dodać przynajmniej jedno zdjęcie (ścieżkę i opis)");
            return "articleImages";
        }

        return "redirect:/article";
    }
}