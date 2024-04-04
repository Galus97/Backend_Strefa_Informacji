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

    public void saveArticleImages(ArticleImages articleImages){
        articleImagesRepository.save(articleImages);
    }

    public List<ArticleImages> findArticleImages(SpecificArticle specificArticle){
        if (Objects.isNull(specificArticle)) {
            throw new NullPointerException("Object SpecificArticle is null");
        }
        List<ArticleImages> allArticleImagesBySpecificArticle = articleImagesRepository.findAllArticleImagesBySpecificArticle(specificArticle);
        return allArticleImagesBySpecificArticle;
    }
}
