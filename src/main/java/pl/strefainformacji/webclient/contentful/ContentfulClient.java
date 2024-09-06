package pl.strefainformacji.webclient.contenful2;

import com.contentful.java.cda.CDAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ContentfulClient2 {

    @Value("${access.token.contentful}")
    private String ACCESS_TOKEN;

    @Value("${space.id}")
    private String SPACE_ID;


    public CDAClient createClient(){
        return CDAClient.builder()
                .setSpace(SPACE_ID)
                .setToken(ACCESS_TOKEN)
                .build();
    }
}
