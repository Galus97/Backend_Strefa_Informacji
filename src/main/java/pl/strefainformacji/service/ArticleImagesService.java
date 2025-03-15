package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.ErrorMessages;
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
        throwIfObjectIsNull(articleImages, ErrorMessages.ARTICLE_IMAGES_IS_NULL);
        articleImagesRepository.save(articleImages);
    }

    public List<ArticleImages> getAllArticleImagesBySpecificArticle(SpecificArticle specificArticle) {
        throwIfObjectIsNull(specificArticle, ErrorMessages.SPECIFIC_ARTICLE_IS_NULL);

        throwIfImagesNotFound(specificArticle.getSpecificArticleId());

        return articleImagesRepository.findAllBySpecificArticle(specificArticle);
    }

    public void throwIfImagesNotFound(Long id) {
        if (!articleImagesRepository.existsBySpecificArticle_SpecificArticleId(id)) {
            throw new ArticleImagesNotFoundException(messageService.getMessage(ErrorMessages.ARTICLE_IMAGES_NOT_FOUND, id));
        }
    }

    public void throwIfObjectIsNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(messageService.getMessage(message));
        }
    }
}