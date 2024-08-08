package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
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

    public ArticleInformation getArticleInformationByArticleId(Long id){
        if(id <= 0){
            throw new IllegalArgumentException("The article number must be greater than zero.");
        }
        return articleInformationRepository.findArticleInformationByArticleId(id);
    }

    public void saveArticle(ArticleInformation articleInformation){
        articleInformationRepository.save(articleInformation);
    }

    public List<ArticleInformation> findAllArticlesByEmployee(Employee employee){
        return articleInformationRepository.findAllByEmployee(employee);
    }

    public List<String> findAllContentfulIds(){
        return articleInformationRepository.findAllContentfulIds();
    }


}
