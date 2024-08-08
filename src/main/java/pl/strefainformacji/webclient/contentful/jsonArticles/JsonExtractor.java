package pl.strefainformacji.webclient.contentful.jsonArticles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.strefainformacji.webclient.contentful.jsonArticles.dto.ContentfulArticleDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class JsonExtractor {

    public static List<String> extractIdsAndTitles(String jsonContent) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        List<String> idsOfElements = new ArrayList<>();

            Map<String, Object> rootMap = mapper.readValue(jsonContent, Map.class);
            List<Map<String, Object>> itemsList = (List<Map<String, Object>>) rootMap.get("items");

            for(Map<String, Object> itemMap : itemsList){
                Map<String, Object> sysMap = (Map<String, Object>) itemMap.get("sys");
                String id = (String) sysMap.get("id");

                idsOfElements.add(id);
            }
            return idsOfElements;
    }

    public static ContentfulArticleDto getFildsFromJson(String jsonContent) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> rootMap = mapper.readValue(jsonContent, Map.class);

        ContentfulArticleDto contentfulArticleDto = new ContentfulArticleDto();

        ContentfulArticleDto.Sys sys = new ContentfulArticleDto.Sys();
        Map<String, Object> sysMap = (Map<String, Object>)rootMap.get("sys");
        sys.setId((String) sysMap.get("id"));

        ContentfulArticleDto.Fields fields = new ContentfulArticleDto.Fields();
        Map<String, Object> fieldsMap = (Map<String, Object>)rootMap.get("fields");


        fields.setHeadTitle((String)fieldsMap.get("headTitle"));
        fields.setShortDescription((String) fieldsMap.get("shortDescription"));
        fields.setImportance((Integer) fieldsMap.get("importance"));
        fields.setHeadAltImg((String) fieldsMap.get("headAltImg"));
        fields.setSpecificTitle((String) fieldsMap.get("specificTitle"));
        fields.setDescription((String) fieldsMap.get("description"));

        Map<String, String> headImgSrcMap = (Map<String, String>)fieldsMap.get("headImgSrc");
        ContentfulArticleDto.Fields.Sys headImgSrc = new ContentfulArticleDto.Fields.Sys();
        headImgSrc.setType(headImgSrcMap.get("type"));
        headImgSrc.setLinkType(headImgSrcMap.get("linkType"));
        headImgSrc.setId(headImgSrcMap.get("id"));
        fields.setHeadImgSrc(headImgSrc);

        List<Map<String, Map<String, String>>> imgSrcListMap = (List<Map<String, Map<String, String>>>) fieldsMap.get("imgSrc");
        System.out.println("imgSrcListMap.size() --> " + imgSrcListMap.size());
        List<ContentfulArticleDto.Fields.Sys> imgSrcList = new ArrayList<>();
        System.out.println("List<ContentfulArticleDto.Fields.Sys> imgSrcList.size --> " + imgSrcList.size());
        for(Map<String, Map<String, String>> imgSrcMap : imgSrcListMap){
            ContentfulArticleDto.Fields.Sys imgSrc = new ContentfulArticleDto.Fields.Sys();
            Map<String, String> mapSys = imgSrcMap.get("sys");
            imgSrc.setType(mapSys.get("type"));
            System.out.println("imgSrcMap.get(\"type\") --> " + imgSrcMap.get("type"));
            imgSrc.setLinkType(mapSys.get("linkType"));
            System.out.println("imgSrcMap.get(\"linkType\" --> )" + imgSrcMap.get("linkType"));
            imgSrc.setId(mapSys.get("id"));
            System.out.println("imgSrcMap.get(\"id\") --> " + imgSrcMap.get("id"));
            imgSrcList.add(imgSrc);
        }

        fields.setImgSrcList(imgSrcList);

        contentfulArticleDto.setFields(fields);
        contentfulArticleDto.setSys(sys);

        return contentfulArticleDto;

    }
}