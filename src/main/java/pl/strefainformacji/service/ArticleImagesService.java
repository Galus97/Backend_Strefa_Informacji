package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.repository.ArticleImagesRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleImagesService {

    private ArticleImagesRepository articleImagesRepository;
    public List<ArticleImages> getImages(Long id){
        return articleImagesRepository.findAllBySpecificArticle_SpecificArticleId(id);
    }
}
