package pl.strefainformacji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.strefainformacji.entity.ArticleInformation;

public interface ArticleRepository extends JpaRepository<ArticleInformation, Long> {

}
