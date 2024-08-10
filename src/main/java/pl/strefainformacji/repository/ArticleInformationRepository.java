package pl.strefainformacji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import java.util.List;

public interface ArticleInformationRepository extends JpaRepository<ArticleInformation, Long> {

    ArticleInformation findArticleInformationByArticleId(Long articleId);

    List<ArticleInformation> findAllByEmployee(Employee employee);

    @Query("SELECT ai.contentfulId FROM ArticleInformation ai")
    List<String> findAllContentfulIds();

}
