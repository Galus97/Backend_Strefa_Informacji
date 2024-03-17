package pl.strefainformacji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;

import java.util.List;

public interface ArticleImagesRepository extends JpaRepository<ArticleImages, Long> {

    List<ArticleImages> findAllArticleImagesBySpecificArticle(SpecificArticle specificArticle);
}
