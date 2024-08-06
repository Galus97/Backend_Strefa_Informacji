package pl.strefainformacji.webclient.contentful.jsonArticles.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class Root {

    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        private String sysId;
        private String fieldsHeadTitle;
    }
}