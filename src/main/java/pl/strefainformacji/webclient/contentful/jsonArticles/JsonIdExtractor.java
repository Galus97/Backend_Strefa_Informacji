package pl.strefainformacji.webclient.contentful.jsonArticles;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class JsonIdExtractor {

    public static List<String> extractIdsAndTitles(String jsonContent) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        List<String> idsOfElements = new ArrayList<>();


            Map<String, Object> rootMap = mapper.readValue(jsonContent, Map.class);
            List<Map<String, Object>> itemsList = (List<Map<String, Object>>) rootMap.get("items");

            for(Map<String, Object> itemMap : itemsList){
                Map<String, Object> sysMap = (Map<String, Object>) itemMap.get("sys");
                String id = (String) sysMap.get("id");

                Map<String, Object> fieldsMap = (Map<String, Object>) itemMap.get("fields");
                String headTitle = (String) fieldsMap.get("headTitle");

                System.out.println("ID: " + id + ", Title: " + headTitle);
                idsOfElements.add(id);

            }
            return idsOfElements;
    }
}
