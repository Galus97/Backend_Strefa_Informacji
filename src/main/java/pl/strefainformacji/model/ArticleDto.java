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

    public boolean isValid() {
        if (articleInformation == null || specificArticle == null || images == null) {
            return false;
        }

        boolean isInformationValid =
                articleInformation.getTitle() != null && !articleInformation.getTitle().isBlank() &&
                        articleInformation.getShortDescription() != null && !articleInformation.getShortDescription().isBlank() &&
                        articleInformation.getImportance() != null;

        boolean isSpecificValid =
                specificArticle.getTitle() != null && !specificArticle.getTitle().isBlank() &&
                        specificArticle.getDescription() != null && !specificArticle.getDescription().isBlank();

        boolean areImagesValid = !images.isEmpty() &&
                images.stream().allMatch(img ->
                        img.getImgSrc() != null && !img.getImgSrc().isBlank() &&
                                img.getAltImg() != null && !img.getAltImg().isBlank()
                );

        return isInformationValid && isSpecificValid && areImagesValid;
    }
}
