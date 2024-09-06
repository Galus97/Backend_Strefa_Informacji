package pl.strefainformacji.webclient.contentful;

import com.contentful.java.cda.CDAClient;
import org.springframework.beans.factory.annotation.Value;

public class ContentfulClient2 {

    @Value("${contentful.access.token}")
    private static String ACCESS_TOKEN;
    private static final String SPACE_ID = "wrhey748z2h3";


    public static CDAClient createClient(){
        return CDAClient.builder()
                .setSpace(SPACE_ID)
                .setToken(ACCESS_TOKEN)
                .build();
    }
}
