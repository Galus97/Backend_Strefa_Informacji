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
    public static final String ARTICLE_IMAGES_IS_NULL = "error.articleImagesIsNull";
    public static final String SPECIFIC_ARTICLE_IS_NULL = "error.specificArticleIsNull";
    public static final String ARTICLE_IMAGES_NOT_FOUND = "error.articleImagesNotFound";

    private final ArticleImagesRepository articleImagesRepository;
    private final MessageService messageService;

    public void saveArticleImages(ArticleImages articleImages) {
        throwIfObjectIsNull(articleImages, ARTICLE_IMAGES_IS_NULL);
        articleImagesRepository.save(articleImages);
    }

    public List<ArticleImages> getAllArticleImagesBySpecificArticle(SpecificArticle specificArticle) {
        throwIfObjectIsNull(specificArticle, SPECIFIC_ARTICLE_IS_NULL);

        throwIfImagesNotFound(specificArticle.getSpecificArticleId());

        return articleImagesRepository.findAllBySpecificArticle(specificArticle);
    }

    public void throwIfImagesNotFound(Long id) {
        if (!articleImagesRepository.existsBySpecificArticle_SpecificArticleId(id)) {
            throw new ArticleImagesNotFoundException(messageService.getMessage(ARTICLE_IMAGES_NOT_FOUND, id));
        }
    }

    public void throwIfObjectIsNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(messageService.getMessage(message));
        }
    }
}