package pl.strefainformacji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.strefainformacji.entity.SpecificArticle;

public interface SpecificArticleRepository extends JpaRepository<SpecificArticle, Long> {

    SpecificArticle findByArticleInformation_Id(Long number);

    boolean existsByArticleInformation_Id(Long number);
}
