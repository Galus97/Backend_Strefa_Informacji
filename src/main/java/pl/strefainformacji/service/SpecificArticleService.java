package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.repository.SpecificArticleRepository;

@Service
@AllArgsConstructor
public class SpecificArticleService {

    private final SpecificArticleRepository specificArticleRepository;

    public SpecificArticle getArticle(){

    }
}
