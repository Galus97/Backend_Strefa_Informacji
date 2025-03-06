package pl.strefainformacji.webclient.contentful.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentfulArticleDto {
    private Fields fields;
    private Sys sys;

    @Data
    @Accessors(chain = true)
    public static class Sys {
        @JsonProperty("id")
        private String id;
    }

    @Data
    @Accessors(chain = true)
    public static class Fields {
        @JsonProperty("headTitle")
        private String headTitle;
        @JsonProperty("shortDescription")
        private String shortDescription;
        @JsonProperty("importance")
        private Integer importance;
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
        @JsonIgnore
        private List<String> altImgList;
        @JsonProperty("employeeId")
        private Integer employeeId;

        @Data
        @Accessors(chain = true)
        public static class Sys {
            @JsonProperty("id")
            private String id;
        }
    }
}
