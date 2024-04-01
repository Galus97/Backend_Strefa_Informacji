package pl.strefainformacji.entity;

import pl.strefainformacji.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    private Long articleId;

    @Size(min = 3)
    private String title;

    @Size(min = 10)
    private String shortDescription;

    @Min(1)
    @Max(10)
    private int importance;

    @Size(min = 1)
    private String imgSrc;

    @Size(min = 1)
    private String altImg;

    @OneToOne
    private Employee employee;

}
