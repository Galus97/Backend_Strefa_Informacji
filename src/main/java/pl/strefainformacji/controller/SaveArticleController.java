package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.service.ArticleService;

@Controller
@RequiredArgsConstructor
public class SaveArticleController {
    private final ArticleService articleService;

    @PostMapping("/save")
    public String saveArticle(HttpSession session) {
        ArticleDto articleDto = (ArticleDto) session.getAttribute("articleDto");

        if (articleDto.isValid()) {
            articleService.saveFullArticle(articleDto);
            session.removeAttribute("articleDto");
            return "redirect:/success";
        }

        return "redirect:/error";
    }
}