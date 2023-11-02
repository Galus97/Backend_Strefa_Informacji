package pl.strefainformacji.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "specificArticle")
@Getter
@Setter
@ToString
public class SpecificArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 30)
    private String description;

    @NotBlank
    private String imgSrc;

    @NotBlank
    private String altImg;

    @OneToOne
    @JoinColumn(name = "articleId")
    private ArticleInformation articleInformation;

}
