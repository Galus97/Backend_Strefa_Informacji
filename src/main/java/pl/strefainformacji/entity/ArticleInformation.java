package pl.strefainformacji.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "articleInformation")
@Getter
@Setter
@ToString
public class ArticleInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3)
    private String title;

    @NotBlank
    @Size(min = 10)
    private String shortDescription;

    @NotNull
    private int importance;

    @NotBlank
    @Size(min = 1)
    private String imgSrc;

    @NotBlank
    @Size(min = 1)
    private String altImg;

}
