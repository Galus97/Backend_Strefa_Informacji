package pl.strefainformacji.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleImagesForm {

    @Size(min = 1, max = 10, message = "${Size.images}")
    @Valid
    private List<ImageDto> images;
}
