package pl.strefainformacji.webclient.contentful.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class JsonIdExtractor {

    public static String extractIdsAndTitles(String jsonContent) {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder resultBuilder = new StringBuilder();

        try {
            Map<String, Object> rootMap = mapper.readValue(jsonContent, Map.class);
            List<Map<String, Object>> itemsList = (List<Map<String, Object>>) rootMap.get("items");

            for(Map<String, Object> itemMap : itemsList){
                Map<String, Object> sysMap = (Map<String, Object>) itemMap.get("sys");
                String id = (String) sysMap.get("id");

                Map<String, Object> fieldsMap = (Map<String, Object>) itemMap.get("fields");
                String headTitle = (String) fieldsMap.get("headTitle");

                System.out.println("ID: " + id + ", Title: " + headTitle);
                resultBuilder.append("ID: ").append(id).append(", Title: ").append(headTitle).append("\n");

            }
            return resultBuilder.toString();
        } catch (IOException e){
            e.printStackTrace();
            return "Error processing JSON";
        }

    }
}
