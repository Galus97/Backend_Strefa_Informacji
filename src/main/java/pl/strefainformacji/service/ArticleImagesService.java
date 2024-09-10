package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.repository.ArticleImagesRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ArticleImagesService {

    private final ArticleImagesRepository articleImagesRepository;

    public void saveArticleImages(ArticleImages articleImages){
        if(articleImages != null){
            articleImagesRepository.save(articleImages);
        }
    }

    public List<ArticleImages> getAllArticleImagesBySpecificArticle(SpecificArticle specificArticle){
        if (Objects.isNull(specificArticle)) {
            throw new NullPointerException("Object SpecificArticle is null");
        }

        boolean isImagesExists = articleImagesRepository.existsArticleImagesBySpecificArticle_SpecificArticleId(specificArticle.getSpecificArticleId());
        if(!isImagesExists){
            throw new NoSuchElementException("There is no article images in the database.");
        }

        return articleImagesRepository.findAllArticleImagesBySpecificArticle(specificArticle);
    }
}