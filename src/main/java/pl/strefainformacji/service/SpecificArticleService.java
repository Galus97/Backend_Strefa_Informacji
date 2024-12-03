package pl.strefainformacji.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.repository.SpecificArticleRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SpecificArticleService {

    private final SpecificArticleRepository specificArticleRepository;

    public SpecificArticle getSpecificArticleByArticleInformationId(Long articleInformationId) {
        validateArticleInformationId(articleInformationId);

        if (specificArticleRepository.existsByArticleInformation_ArticleId(articleInformationId)) {
            return specificArticleRepository.findByArticleInformation_ArticleId(articleInformationId);
        } else {
            throw new NoSuchElementException(("Specific article not found for the given ID: " + articleInformationId));
        }

    }

    public void saveSpecificArticle(SpecificArticle specificArticle) {
        specificArticleRepository.save(specificArticle);
    }

    private void validateArticleInformationId(Long articleInformationId) {
        if (articleInformationId == null || articleInformationId <= 0) {
            throw new IllegalArgumentException("The article ID must be a positive number.");
        }
    }
}
