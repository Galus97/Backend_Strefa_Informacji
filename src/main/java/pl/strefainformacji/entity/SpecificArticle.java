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

    @NotNull
    private String imgSrc;

    @NotNull
    private String altImg;

    @OneToOne
    private ArticleInformation articleInformation;

}

