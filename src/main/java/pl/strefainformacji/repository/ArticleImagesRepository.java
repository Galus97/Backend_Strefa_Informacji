package pl.strefainformacji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.strefainformacji.entity.ArticleImages;

import java.util.List;

public interface ArticleImagesRepository extends JpaRepository<ArticleImages, Long> {

    //@Query("SELECT b FROM ArticleImages b where b.specificArticle ")
    //List<ArticleImages> abf();
}
