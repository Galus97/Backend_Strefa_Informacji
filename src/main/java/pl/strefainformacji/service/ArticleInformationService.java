package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.repository.ArticleInformationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ArticleInformationService {

    private final ArticleInformationRepository articleInformationRepository;

    public List<ArticleInformation> getAllArticles (){
        List<ArticleInformation> allArticels = articleInformationRepository.findAll();

        if(allArticels.isEmpty()){
            throw new NoSuchElementException("There are no articles in the database");
        }
        return allArticels;
    }

    public ArticleInformation findArticleInformation(Long id){
        return articleInformationRepository.findArticleInformationByArticleId(id);
    }

    public void saveArticle(ArticleInformation articleInformation){
        articleInformationRepository.save(articleInformation);
    }
}
