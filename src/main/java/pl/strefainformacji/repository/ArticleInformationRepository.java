package pl.strefainformacji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.strefainformacji.entity.ArticleInformation;

public interface ArticleInformationRepository extends JpaRepository<ArticleInformation, Long> {

    ArticleInformation findArticleInformationByArticleId(Long articleId);
}
