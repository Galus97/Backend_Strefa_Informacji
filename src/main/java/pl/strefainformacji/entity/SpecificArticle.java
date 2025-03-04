package pl.strefainformacji.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "specificArticle")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecificArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specificArticleId;

    @Size(min = 3, message = "{Size.specificArticle.title}")
    private String title;

    @Size(min = 30, message = "{Size.specificArticle.description}")
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    @JoinColumn(name = "articleId")
    @JsonIgnore
    private ArticleInformation articleInformation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "specificArticle")
    private List<ArticleImages> articleImages;

}
