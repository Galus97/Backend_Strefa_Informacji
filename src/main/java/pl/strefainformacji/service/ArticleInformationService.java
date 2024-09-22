package pl.strefainformacji.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.repository.ArticleInformationRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ArticleInformationService {

    private final ArticleInformationRepository articleInformationRepository;
    private final EmployeeService employeeService;

    public List<ArticleInformation> getAllArticles() {
        List<ArticleInformation> allArticels = articleInformationRepository.findAll();
        if (allArticels.isEmpty()) {
            throw new NoSuchElementException("There are no articles in the database");
        }
        return allArticels;
    }

    public ArticleInformation getArticleInformationByArticleId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The article number must be greater than zero.");
        }
        return articleInformationRepository.findArticleInformationByArticleId(id);
    }

    public void saveArticle(ArticleInformation articleInformation) {
        if (articleInformation != null) {
            articleInformationRepository.save(articleInformation);
        }
    }

    public List<ArticleInformation> findAllArticlesByEmployee(Employee employee) {
        if (employee != null && employeeService.findByEmployeeId(employee.getEmployeeId()).isPresent()) {
            return articleInformationRepository.findAllByEmployee(employee);
        } else {
            return null;
        }
    }

    public List<String> findAllContentfulIds() {
        List<String> contentfulIds = articleInformationRepository.findAllContentfulIds();
        Collections.reverse(contentfulIds);
        return contentfulIds;
    }

    public List<ArticleInformation> getLastFiveArticlesByEmployee(Employee employee) {
        PageRequest pageRequest = PageRequest.of(0, 5);
        return articleInformationRepository.findLastFiveArticlesByEmployee(employee, pageRequest);
    }
}
