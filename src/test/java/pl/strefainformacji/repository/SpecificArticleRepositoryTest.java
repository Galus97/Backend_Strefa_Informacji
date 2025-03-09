package pl.strefainformacji.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SpecificArticleRepositoryTest {

    @Autowired
    private SpecificArticleRepository specificArticleRepository;

    @Autowired
    private ArticleInformationRepository articleInformationRepository;

    @Test
    public void testFindByArticleInformation_ArticleId() {
        // given
        ArticleInformation articleInformation = ArticleInformation.builder()
                .contentfulId("cid123")
                .title("Title")
                .shortDescription("Valid short description")
                .importance(5)
                .imgSrc("img.jpg")
                .altImg("alt")
                .localDateTime(LocalDateTime.now())
                .build();
        articleInformation = articleInformationRepository.save(articleInformation);

        SpecificArticle specificArticle = SpecificArticle.builder()
                .title("Specific Title")
                .description("This is a valid description with more than thirty characters.")
                .articleInformation(articleInformation)
                .build();
        specificArticle = specificArticleRepository.save(specificArticle);

        // when
        SpecificArticle found = specificArticleRepository.findByArticleInformation_ArticleId(articleInformation.getArticleId());

        // then
        assertThat(found).isNotNull();
        assertThat(found.getSpecificArticleId()).isEqualTo(specificArticle.getSpecificArticleId());
    }

    @Test
    public void testExistsByArticleInformation_ArticleId() {
        // given
        ArticleInformation articleInformation = ArticleInformation.builder()
                .contentfulId("cid456")
                .title("Title")
                .shortDescription("Valid short description")
                .importance(5)
                .imgSrc("img.jpg")
                .altImg("alt")
                .localDateTime(LocalDateTime.now())
                .build();
        articleInformation = articleInformationRepository.save(articleInformation);

        SpecificArticle specificArticle = SpecificArticle.builder()
                .title("Specific Title")
                .description("This is a valid description with more than thirty characters.")
                .articleInformation(articleInformation)
                .build();
        specificArticleRepository.save(specificArticle);

        // when
        boolean exists = specificArticleRepository.existsByArticleInformation_ArticleId(articleInformation.getArticleId());

        // then
        assertThat(exists).isTrue();
    }
}
