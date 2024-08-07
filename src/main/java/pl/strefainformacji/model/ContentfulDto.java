package pl.strefainformacji.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ContentfulDto {

    private String headTitle;

    private String shortDescription;

    private int importance;

    private String headAltImg;

    private String specificTitle;

    private String description;
}
