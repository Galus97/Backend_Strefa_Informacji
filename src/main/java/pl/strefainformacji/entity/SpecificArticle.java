package pl.strefainformacji.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "specificArticle")
@Getter
@Setter
@ToString
public class SpecificArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specificArticleId;

    @Size(min = 3)
    private String title;

    @Size(min = 30)
    private String description;

    @OneToOne
    @JoinColumn(name = "articleId")
    @JsonIgnore
    private ArticleInformation articleInformation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "specificArticle")
    private List<ArticleImages> articleImages;

}
