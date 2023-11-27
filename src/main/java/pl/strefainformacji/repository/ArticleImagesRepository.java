package pl.strefainformacji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.strefainformacji.entity.ArticleImages;

import java.util.List;

public interface ArticleImagesRepository extends JpaRepository<ArticleImages, Long> {

    List<ArticleImages> findAllBySpecificArticle_SpecificArticleId(Long specificArticleId);
}
