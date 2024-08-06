package pl.strefainformacji.webclient.contentful.jsonArticles.dto;

import lombok.Getter;

@Getter
public class ContentfulArticleDto {
    private String sysId;
    private String fieldsHeadTitle;

    private String fieldsShortDescription;

    private int fieldsImportance;

    private String fieldsHeadAltImg;

    private String fieldsSpecificTitle;

    private String fieldsDescription;
}