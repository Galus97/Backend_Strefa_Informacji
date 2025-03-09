package pl.strefainformacji.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.entity.SpecificArticle;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ArticleImagesRepositoryTest {

    @Autowired
    private ArticleImagesRepository articleImagesRepository;

    @Autowired
    private SpecificArticleRepository specificArticleRepository;

    @Autowired
    private ArticleInformationRepository articleInformationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindAllBySpecificArticle() {
        // given: create employee, articleInformation, specificArticle and images.
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("secretPassword")
                .enabled(true)
                .emailCode("code")
                .build();
        employee = employeeRepository.save(employee);

        ArticleInformation articleInformation = ArticleInformation.builder()
                .contentfulId("cid789")
                .title("Title")
                .shortDescription("Short description")
                .importance(5)
                .imgSrc("img.jpg")
                .altImg("alt")
                .employee(employee)
                .localDateTime(LocalDateTime.now())
                .build();
        articleInformation = articleInformationRepository.save(articleInformation);

        SpecificArticle specificArticle = SpecificArticle.builder()
                .title("Specific Title")
                .description("This is a valid description with more than thirty characters.")
                .articleInformation(articleInformation)
                .build();
        specificArticle = specificArticleRepository.save(specificArticle);

        ArticleImages image1 = ArticleImages.builder()
                .imgSrc("image1.jpg")
                .altImg("alt1")
                .specificArticle(specificArticle)
                .build();
        ArticleImages image2 = ArticleImages.builder()
                .imgSrc("image2.jpg")
                .altImg("alt2")
                .specificArticle(specificArticle)
                .build();
        articleImagesRepository.save(image1);
        articleImagesRepository.save(image2);

        // when
        List<ArticleImages> images = articleImagesRepository.findAllBySpecificArticle(specificArticle);

        // then
        assertThat(images).hasSize(2);
    }

    @Test
    public void testExistsBySpecificArticle_SpecificArticleId() {
        // given: create employee, articleInformation, specificArticle (without images)
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("secretPassword")
                .enabled(true)
                .emailCode("code")
                .build();
        employee = employeeRepository.save(employee);

        ArticleInformation articleInformation = ArticleInformation.builder()
                .contentfulId("cid101")
                .title("Title")
                .shortDescription("Short description")
                .importance(5)
                .imgSrc("img.jpg")
                .altImg("alt")
                .employee(employee)
                .localDateTime(LocalDateTime.now())
                .build();
        articleInformation = articleInformationRepository.save(articleInformation);

        SpecificArticle specificArticle = SpecificArticle.builder()
                .title("Specific Title")
                .description("This is a valid description with more than thirty characters.")
                .articleInformation(articleInformation)
                .build();
        specificArticle = specificArticleRepository.save(specificArticle);

        // when: there are no images saved for this specificArticle
        boolean exists = articleImagesRepository.existsBySpecificArticle_SpecificArticleId(specificArticle.getSpecificArticleId());

        // then
        assertThat(exists).isFalse();
    }
}
