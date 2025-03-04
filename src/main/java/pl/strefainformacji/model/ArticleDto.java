package pl.strefainformacji.model;

import lombok.Getter;
import lombok.Setter;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArticleDto {
    private ArticleInformation articleInformation;
    private SpecificArticle specificArticle;
    private List<ArticleImages> images = new ArrayList<>();
}
