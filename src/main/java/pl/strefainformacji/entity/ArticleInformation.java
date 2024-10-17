package pl.strefainformacji.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Entity
@Table(name = "articleInformation")
@Getter
@Setter
@ToString
public class ArticleInformation {

    private final static Logger logger = LoggerFactory.getLogger(ArticleInformation.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Size(min = 5)
    private String contentfulId;

    @Size(min = 3, message = "{Size.articleInformation.title}")
    private String title;

    @Size(min = 10, message = "{Size.articleInformation.shortDescription}")
    private String shortDescription;

    @Min(value = 1, message = "{Min.articleInformation.importance}")
    @Max(value = 10, message = "{Max.articleInformation.importance}")
    private Integer importance;

    @Size(min = 1, message = "{Size.articleInformation.imgSrc}")
    private String imgSrc;

    @Size(min = 1, message = "{Size.articleInformation.altImg}")
    private String altImg;

    @ManyToOne
    private Employee employee;

    private LocalDateTime localDateTime;
}
