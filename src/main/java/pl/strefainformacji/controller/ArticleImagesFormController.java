package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ArticleImagesFormController {
    private final EmployeeService employeeService;
    private final MessageService messageService;

    @GetMapping("/add/articleImages")
    public String showArticleImagesForm(@AuthenticationPrincipal CurrentEmployee currentEmployee) {
        if (employeeService.isEnabledById(currentEmployee.getEmployee().getEmployeeId())) {
            return "articleImages";
        } else {
            return "redirect:/verifyEmail";
        }
    }

    @PostMapping("/add/articleImages")
    public String saveArticleImages(@RequestParam Map<String, String> allParams, Model model, HttpSession session) {
        List<ArticleImages> articleImagesList = new ArrayList<>();

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

        if (articleImagesList.isEmpty()) {
            String errorMessage = messageService.getMessage("error.articleImages.empty", null, Locale.getDefault());
            model.addAttribute("errorImage", errorMessage);
            return "articleImages";
        }

        ArticleDto articleDto = (ArticleDto) session.getAttribute("articleDto");
        if (articleDto == null) {
            articleDto = new ArticleDto();
        }

        articleDto.setImages(articleImagesList);
        session.setAttribute("articleDto", articleDto);

        return "redirect:/article";
    }
}