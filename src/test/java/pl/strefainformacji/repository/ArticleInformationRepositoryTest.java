package pl.strefainformacji.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ArticleInformationRepositoryTest {

    @Autowired
    private ArticleInformationRepository articleInformationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setUsername("john.doe");
        employee.setEmail("john.doe@example.com");
        employeeRepository.saveAndFlush(employee);

        ArticleInformation article1 = new ArticleInformation();
        article1.setEmployee(employee);
        article1.setContentfulId("article1");

        ArticleInformation article2 = new ArticleInformation();
        article2.setEmployee(employee);
        article2.setContentfulId("article2");

        articleInformationRepository.saveAndFlush(article1);
        articleInformationRepository.saveAndFlush(article2);
    }

    @Test
    void testFindArticleInformationByArticleId() {
        ArticleInformation article = new ArticleInformation();
        article.setContentfulId("test-article");
        article.setEmployee(employee);
        articleInformationRepository.saveAndFlush(article);

        ArticleInformation foundArticle = articleInformationRepository.findArticleInformationByArticleId(article.getArticleId());

        assertThat(foundArticle).isNotNull();
        assertThat(foundArticle.getContentfulId()).isEqualTo("test-article");
    }

    @Test
    void testFindAllByEmployee() {
        List<ArticleInformation> articles = articleInformationRepository.findAllByEmployee(employee);

        assertThat(articles).hasSize(2);
        assertThat(articles.get(0).getEmployee().getUsername()).isEqualTo("john.doe");
    }

    @Test
    void testFindAllContentfulIds() {
        List<String> contentfulIds = articleInformationRepository.findAllContentfulIds();

        assertThat(contentfulIds).hasSize(2);
        assertThat(contentfulIds).contains("article1", "article2");
    }

    @Test
    void testFindLastFiveArticlesByEmployee() {
        List<ArticleInformation> lastFiveArticles = articleInformationRepository.findLastFiveArticlesByEmployee(employee, PageRequest.of(0, 5));

        assertThat(lastFiveArticles).hasSize(2);
        assertThat(lastFiveArticles.get(0).getContentfulId()).isEqualTo("article2");
    }
}