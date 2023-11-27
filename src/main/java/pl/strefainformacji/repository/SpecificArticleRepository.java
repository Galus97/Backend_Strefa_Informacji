package pl.strefainformacji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.SpecificArticle;

import java.util.List;

public interface SpecificArticleRepository extends JpaRepository<SpecificArticle, Long> {

    SpecificArticle findByArticleInformation_ArticleId(Long number);

    boolean existsByArticleInformation_ArticleId(Long number);

//    @Query(value = "SELECT ai FROM SpecificArticle ai WHERE ai.specificArticleId = :specificArticleId")
//    List<ArticleImages> findSpecificArticleImagesByArticleId(@Param("specificArticleId") Long specificArticleId );
}
