package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.exception.ArticleImagesNotFoundException;
import pl.strefainformacji.repository.ArticleImagesRepository;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ArticleImagesService {

    private final ArticleImagesRepository articleImagesRepository;
    private final MessageService messageService;

    public void saveArticleImages(ArticleImages articleImages) {
        if (Objects.isNull(articleImages)) {
            throw new IllegalArgumentException(messageService.getMessage("error.articleImagesIsNull"));
        }
        articleImagesRepository.save(articleImages);
    }

    public List<ArticleImages> getAllArticleImagesBySpecificArticle(SpecificArticle specificArticle) {
        if (Objects.isNull(specificArticle)) {
            throw new IllegalArgumentException(messageService.getMessage("error.specificArticleIsNull"));
        }

        isArticleImagesExistOrTrow(specificArticle.getSpecificArticleId());

        return articleImagesRepository.findAllBySpecificArticle(specificArticle);
    }

    public void isArticleImagesExistOrTrow(Long id) {
        if (!articleImagesRepository.existsBySpecificArticle_SpecificArticleId(id)) {
            throw new ArticleImagesNotFoundException(messageService.getMessage("error.articleImagesNotFound", id));
        }
    }
}