package pl.strefainformacji.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ContentfulDto {

    private String sysId;
    private String fieldsHeadTitle;

    private String fieldsShortDescription;

    private int fieldsImportance;

    private String fieldsHeadAltImg;

    private String fieldsSpecificTitle;

    private String fieldsDescription;
}
