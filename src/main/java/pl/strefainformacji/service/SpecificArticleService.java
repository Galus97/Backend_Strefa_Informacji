package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.repository.SpecificArticleRepository;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SpecificArticleService {

    private final SpecificArticleRepository specificArticleRepository;

    public SpecificArticle getSpecificArticleByArticleInformationId(Long ArticleInformationId){
        if (ArticleInformationId <= 0) {
            throw new IllegalArgumentException("The article number must be greater than zero.");
        }

        boolean isSpecificArticleExists =specificArticleRepository.existsByArticleInformation_ArticleId(ArticleInformationId);
        if (!isSpecificArticleExists) {
            throw new NoSuchElementException("There is no specific article in the database.");
        }

        SpecificArticle article = specificArticleRepository.findByArticleInformation_ArticleId(ArticleInformationId);
        if(Objects.isNull(article)){
            throw new NullPointerException("This is not a number");
        }

        return article;
    }

    public void saveSpecificArticle(SpecificArticle specificArticle){
        specificArticleRepository.save(specificArticle);
    }

    public SpecificArticle getSpecificArticle(Long specificArticleId){
        if(specificArticleId < 0){
            throw new IllegalArgumentException();
        } else if(Objects.isNull(specificArticleId)){
            throw new NullPointerException("Object SpecificArticle is null");
        } else {
            return specificArticleRepository.findBySpecificArticleId(specificArticleId);
        }
    }

}
