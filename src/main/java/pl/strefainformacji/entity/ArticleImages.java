package pl.strefainformacji.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "articleImages")
@Getter
@Setter
@ToString
public class ArticleImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleImagesId;

    @Size(min = 1)
    private String imgSrc;

    @Size(min = 1)
    private String altImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specificArticleId")
    @JsonIgnore
    private SpecificArticle specificArticle;
}
