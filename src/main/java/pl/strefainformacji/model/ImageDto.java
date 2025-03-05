package pl.strefainformacji.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {
    @NotBlank(message = "${NotBlank.imagesSrc}")
    private String imgSrc;

    @NotBlank(message = "${NotBlank.imagesAlt}")
    private String altImg;

}
