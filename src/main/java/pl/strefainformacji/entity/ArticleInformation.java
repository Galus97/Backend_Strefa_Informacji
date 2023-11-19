package pl.strefainformacji.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Base64;

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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="blob")
    private byte[] imageData;

    @Size(min = 1)
    private String imgSrc;

    @Size(min = 1)
    private String altImg;

    public void setImageDataBase64(String base64){
        this.imageData = Base64.getDecoder().decode(base64);
    }

}
