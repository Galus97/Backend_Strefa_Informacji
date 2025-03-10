package pl.strefainformacji.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ArticleInformationRepositoryTest {

    @Autowired
    private ArticleInformationRepository articleInformationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindAllByEmployee() {
        // given: create an employee and some articles
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

        ArticleInformation article1 = ArticleInformation.builder()
                .contentfulId("cid01")
                .title("Title 1")
                .shortDescription("Short description 1")
                .importance(5)
                .imgSrc("img1.jpg")
                .altImg("alt1")
                .employee(employee)
                .localDateTime(LocalDateTime.now())
                .build();
        ArticleInformation article2 = ArticleInformation.builder()
                .contentfulId("cid02")
                .title("Title 2")
                .shortDescription("Short description 2")
                .importance(6)
                .imgSrc("img2.jpg")
                .altImg("alt2")
                .employee(employee)
                .localDateTime(LocalDateTime.now())
                .build();
        articleInformationRepository.save(article1);
        articleInformationRepository.save(article2);

        // when
        List<ArticleInformation> articles = articleInformationRepository.findAllByEmployee(employee);

        // then
        assertThat(articles).hasSize(2);
    }

    @Test
    public void testFindAllContentfulIds() {
        // given: create articles with contentfulIds
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

        ArticleInformation article1 = ArticleInformation.builder()
                .contentfulId("cid01")
                .title("Title 1")
                .shortDescription("Short description 1")
                .importance(5)
                .imgSrc("img1.jpg")
                .altImg("alt1")
                .employee(employee)
                .localDateTime(LocalDateTime.now())
                .build();
        ArticleInformation article2 = ArticleInformation.builder()
                .contentfulId("cid02")
                .title("Title 2")
                .shortDescription("Short description 2")
                .importance(6)
                .imgSrc("img2.jpg")
                .altImg("alt2")
                .employee(employee)
                .localDateTime(LocalDateTime.now())
                .build();
        articleInformationRepository.save(article1);
        articleInformationRepository.save(article2);

        // when
        List<String> contentfulIds = articleInformationRepository.findAllContentfulIds();

        // then
        assertThat(contentfulIds).containsExactlyInAnyOrder("cid01", "cid02");
    }

    @Test
    public void testFindLastFiveArticlesByEmployee() {
        // given: create an employee and 10 articles
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

        for (int i = 1; i <= 10; i++) {
            ArticleInformation article = ArticleInformation.builder()
                    .contentfulId("cid" + String.format("%02d", i)) // e.g. "cid01", "cid02", ...
                    .title("Title " + i)
                    .shortDescription("Short description " + i)
                    .importance(5)
                    .imgSrc("img" + i + ".jpg")
                    .altImg("alt" + i)
                    .employee(employee)
                    .localDateTime(LocalDateTime.now())
                    .build();
            articleInformationRepository.save(article);
        }

        // when: get last five articles in descending order by articleId
        List<ArticleInformation> lastFive = articleInformationRepository.findLastFiveArticlesByEmployee(
                employee, PageRequest.of(0, 5));

        // then
        assertThat(lastFive).hasSize(5);
        Long previousId = Long.MAX_VALUE;
        for (ArticleInformation article : lastFive) {
            assertThat(article.getArticleId()).isLessThanOrEqualTo(previousId);
            previousId = article.getArticleId();
        }
    }
}
