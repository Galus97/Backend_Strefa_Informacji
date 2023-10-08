package pl.strefainformacji.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @Size(min = 30)
    private String description;

    @NotBlank
    @Size(min = 1)
    private String imgSrc;

    @NotBlank
    @Size(min = 1)
    private String altImg;

    @OneToOne
    @JoinColumn(name = "articleInformation_id")
    private ArticleInformation articleInformation;

}
