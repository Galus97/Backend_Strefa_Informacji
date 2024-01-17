package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.repository.ArticleImagesRepository;

@Service
@AllArgsConstructor
public class ArticleImagesService {

    private final ArticleImagesRepository articleImagesRepository;

    public void saveArticleImages(ArticleImages articleImages){
        articleImagesRepository.save(articleImages);
    }
}
