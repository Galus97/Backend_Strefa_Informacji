package pl.strefainformacji.webclient.contentful.jsonArticles.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentfulArticleDto {
    private Fields fields;
    private Sys sys;

    @Getter
    @Setter
    @ToString
    public static class Sys {
        @JsonProperty("id")
        private String id;
    }

    @Getter
    @Setter
    @ToString
    public static class Fields {
        @JsonProperty("headTitle")
        private String headTitle;
        @JsonProperty("shortDescription")
        private String shortDescription;
        @JsonProperty("importance")
        private int importance;
        @JsonProperty("headImgSrc")
        private Sys headImgSrc;
        @JsonProperty("headAltImg")
        private String headAltImg;
        @JsonProperty("specificTitle")
        private String specificTitle;
        @JsonProperty("description")
        private String description;
        @JsonProperty("imgSrc")
        private List<Sys> imgSrcList;

        @Getter
        @Setter
        @ToString
        public static class Sys {
            @JsonProperty("type")
            private String type;
            @JsonProperty("linkType")
            private String linkType;
            @JsonProperty("id")
            private String id;
        }
    }
}