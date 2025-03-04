package pl.strefainformacji.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public SpecificArticle getSpecificArticleByArticleInformationId(Long articleInformationId) {
        if (articleInformationId == null || articleInformationId <= 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidArticleId", articleInformationId));
        }
        isSpecificArticleExistOrThrow(articleInformationId);

        return specificArticleRepository.findByArticleInformation_ArticleId(articleInformationId);
    }

    public void saveSpecificArticle(SpecificArticle specificArticle) {
        if (Objects.isNull(specificArticle)) {
            throw new IllegalArgumentException(messageService.getMessage("error.specificArticleIsNull"));
        }
        specificArticleRepository.save(specificArticle);
    }

    public void isSpecificArticleExistOrThrow(Long id) {
        if (!specificArticleRepository.existsByArticleInformation_ArticleId(id)) {
            throw new SpecificArticleNotFoundException(messageService.getMessage("error.specificArticleNotFound", id));
        }
    }
}
