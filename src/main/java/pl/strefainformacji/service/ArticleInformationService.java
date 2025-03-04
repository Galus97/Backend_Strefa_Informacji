package pl.strefainformacji.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.repository.ArticleInformationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleInformationService {

    private final ArticleInformationRepository articleInformationRepository;
    private final EmployeeService employeeService;
    private final MessageService messageService;

    public List<ArticleInformation> getAllArticles() {
        return articleInformationRepository.findAll();
    }

    public ArticleInformation getArticle(Long articleId) {
        if (articleId == null || articleId <= 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidArticleId", articleId));
        }
        return articleInformationRepository.findArticleInformationByArticleId(articleId);
    }

    public void saveArticleInformation(ArticleInformation articleInformation) {
        if (articleInformation == null) {
            throw new IllegalArgumentException("error.articleIsNull");
        }
        articleInformationRepository.save(articleInformation);
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

    public List<ArticleInformation> getAddedArticleInPeriod(Employee employee, LocalDateTime weekStart) {
        List<ArticleInformation> allArticlesByEmployee = articleInformationRepository.findAllByEmployee(employee);
        List<ArticleInformation> articlesInGivenWeek = new ArrayList<>();

        if (weekStart != null) {
            LocalDateTime weekEnd = weekStart.plusDays(7);

            for (ArticleInformation articleInformation : allArticlesByEmployee) {
                LocalDateTime articleDateTime = articleInformation.getLocalDateTime();

                if (articleDateTime != null && !articleDateTime.isBefore(weekStart) && articleDateTime.isBefore(weekEnd)) {
                    articlesInGivenWeek.add(articleInformation);
                }
            }
        }
        return articlesInGivenWeek;
    }
}
