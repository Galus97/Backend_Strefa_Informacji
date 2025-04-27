package pl.strefainformacji.controller.save;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.model.ArticleImagesForm;
import pl.strefainformacji.model.ImageDto;
import pl.strefainformacji.service.EmployeeService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ArticleImagesFormController {
    private static final String ARTICLE_IMAGES_PAGE = "articleImages";
    private final EmployeeService employeeService;
    private final MessageService messageService;

    @GetMapping("/add/articleImages")
    public String showArticleImagesForm(@AuthenticationPrincipal CurrentEmployee currentEmployee, Model model, HttpSession session) {
        if (employeeService.isEnabledById(currentEmployee.getEmployee().getEmployeeId())) {
            ArticleImagesForm form = (ArticleImagesForm) session.getAttribute("articleImagesForm");
            if (form == null) {
                form = new ArticleImagesForm();
                form.setImages(Collections.nCopies(10, new ImageDto()));
            }
            model.addAttribute("form", form);
            return ARTICLE_IMAGES_PAGE;
        } else {
            return "redirect:/verifyEmail";
        }
    }

    @PostMapping("/add/articleImages")
    public String saveArticleImages(@Valid @ModelAttribute("form") ArticleImagesForm form, BindingResult bindingResult,
                                    HttpSession session, Locale locale) {

        if (bindingResult.hasErrors()) {
            return ARTICLE_IMAGES_PAGE;
        }

        List<ImageDto> validImages = form.getImages().stream()
                .filter(img -> !img.getImgSrc().isEmpty() && !img.getAltImg().isEmpty())
                .collect(Collectors.toList());

        if (validImages.size() < 1) {
            bindingResult.rejectValue("images", "error.images",
                    messageService.getMessage("error.images.min", null, locale));
            return ARTICLE_IMAGES_PAGE;
        }
        ArticleDto articleDto = (ArticleDto) session.getAttribute("articleDto");
        if (articleDto == null) {
            articleDto = new ArticleDto();
        }


        articleDto.setImages(validImages.stream()
                .map(dto -> new ArticleImages(dto.getImgSrc(), dto.getAltImg()))
                .collect(Collectors.toList()));

        session.setAttribute("articleDto", articleDto);

        return "redirect:/article";
    }
}