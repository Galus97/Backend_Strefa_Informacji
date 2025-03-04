package pl.strefainformacji.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "articleImages")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Article specificArticle;
}
