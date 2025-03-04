package pl.strefainformacji.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.repository.ArticleImagesRepository;
import pl.strefainformacji.repository.ArticleInformationRepository;
import pl.strefainformacji.repository.SpecificArticleRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleInformationRepository articleInformationRepository;
    private final SpecificArticleRepository specificArticleRepository;
    private final ArticleImagesRepository articleImagesRepository;

    public void saveFullArticle(ArticleDto articleDto) {
        ArticleInformation info = articleInformationRepository.save(articleDto.getArticleInformation());
        SpecificArticle specific = specificArticleRepository.save(articleDto.getSpecificArticle());
        specific.setArticleInformation(info);

        articleDto.getImages().forEach(img -> {
            img.setSpecificArticle(specific);
            articleImagesRepository.save(img);
        });
    }
}
