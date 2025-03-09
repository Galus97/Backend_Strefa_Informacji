package pl.strefainformacji.webclient.contentful.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ContentfulArticleDto {
    private Sys sys;
    private Fields fields;

    @Data
    @Accessors(chain = true)
    public static class Sys {
        private String id;
    }

    @Data
    @Accessors(chain = true)
    public static class Fields {
        private String headTitle;
        private String shortDescription;
        private Integer importance;
        private Sys headImgSrc;
        private List<Sys> imgSrcList;
        private List<String> altImgList;
        private Integer employeeId;
        private String headAltImg;
        private String specificTitle;
        private String description;

        @Data
        @Accessors(chain = true)
        public static class Sys {
            private String id;
        }
    }
}
