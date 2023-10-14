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

    public SpecificArticle getArticle(Long number){
        if (number <= 0) {
            throw new IllegalArgumentException("The article number must be greater than zero.");
        }

        boolean exists =specificArticleRepository.existsByArticleInformation_Id(number);
        if (!exists) {
            throw new NoSuchElementException("There is no specific article in the database.");
        }

        SpecificArticle article = specificArticleRepository.findByArticleInformation_Id(number);
        if(Objects.isNull(article)){
            throw new NullPointerException("There is not a number");
        }

        return article;
    }
}
