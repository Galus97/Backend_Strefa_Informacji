package pl.strefainformacji.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Size(min = 3)
    private String title;

    @Size(min = 10)
    private String shortDescription;

    @Min(1)
    @Max(10)
    private int importance;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

    @Size(min = 1)
    private String imgSrc;

    @Size(min = 1)
    private String altImg;

}
