package pl.strefainformacji.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.ErrorMessages;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.exception.SpecificArticleNotFoundException;
import pl.strefainformacji.repository.SpecificArticleRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SpecificArticleService {
    private final SpecificArticleRepository specificArticleRepository;
    private final MessageService messageService;

    public SpecificArticle getSpecificArticleByArticleInformationId(Long articleId) {
        throwIfIdIsInvalid(articleId, ErrorMessages.ARTICLE_ID_IS_INVALID);

        isSpecificArticleExistOrThrow(articleId, ErrorMessages.SPECIFIC_ARTICLE_NOT_FOUND);

        return specificArticleRepository.findByArticleInformation_ArticleId(articleId);
    }

    public void saveSpecificArticle(SpecificArticle specificArticle) {
        if (Objects.isNull(specificArticle)) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.SPECIFIC_ARTICLE_IS_NULL));
        }
        specificArticleRepository.save(specificArticle);
    }

    private void throwIfIdIsInvalid(Long id, String message) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(messageService.getMessage(message, id));
        }
    }

    private void isSpecificArticleExistOrThrow(Long id, String message) {
        if (!specificArticleRepository.existsByArticleInformation_ArticleId(id)) {
            throw new SpecificArticleNotFoundException(messageService.getMessage(message, id));
        }
    }
}
