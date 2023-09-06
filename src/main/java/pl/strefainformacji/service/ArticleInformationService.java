package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.repository.ArticleInformationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleInformationService {

    private final ArticleInformationRepository articleInformationRepository;

    public List<ArticleInformation> getAllArticles (){
        return articleInformationRepository.findAll();
    }
}
