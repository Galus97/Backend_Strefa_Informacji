package pl.strefainformacji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;

import java.util.List;

public interface ArticleImagesRepository extends JpaRepository<ArticleImages, Long> {

    List<ArticleImages> findAllBySpecificArticle(SpecificArticle specificArticle);

    boolean existsBySpecificArticle_SpecificArticleId(Long number);
}
