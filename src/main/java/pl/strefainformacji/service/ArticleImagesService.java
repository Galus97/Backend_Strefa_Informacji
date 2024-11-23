package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.repository.ArticleImagesRepository;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ArticleImagesService {

    private final ArticleImagesRepository articleImagesRepository;

    public void saveArticleImages(ArticleImages articleImages) {
        if (Objects.nonNull(articleImages)) {
            articleImagesRepository.save(articleImages);
        }
    }

    public List<ArticleImages> getAllArticleImagesBySpecificArticle(SpecificArticle specificArticle) {
        if (Objects.isNull(specificArticle)) {
            throw new IllegalArgumentException("SpecificArticle cannot be null.");
        }

        Long specificArticleId = specificArticle.getSpecificArticleId();
        if (Objects.isNull(specificArticleId)) {
            throw new IllegalArgumentException("SpecificArticle must have a valid ID.");
        }

        return articleImagesRepository.findAllArticleImagesBySpecificArticle(specificArticle);
    }
}