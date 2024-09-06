package pl.strefainformacji.service;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import org.springframework.stereotype.Service;
import pl.strefainformacji.webclient.contentful.ContentfulClient;
import pl.strefainformacji.webclient.contentful.jsonArticles.dto.ContentfulArticleDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentfulService {
    private final CDAClient client;

    public ContentfulService(ContentfulClient contentfulClient2) {
        this.client = contentfulClient2.createClient();
    }

    public List<String> getAllArticlesIds(){
        List<CDAResource> entries = client.fetch(CDAEntry.class)
                .where("content_type", "article")
                .where("order", "-sys.createdAt")
                .limit(10)
                .all()
                .items();

        List<String> articleIds = new ArrayList<>();
        for(CDAResource resource : entries){
            CDAEntry entry = (CDAEntry) resource;
            articleIds.add(entry.id());
        }
        return articleIds;
    }

    public ContentfulArticleDto getArticleById(String id){
        CDAEntry entry = client.fetch(CDAEntry.class).one(id);
        if(entry == null){
            return null;
        }
        return mapToContentfulArticleDto(entry);
    }


    private ContentfulArticleDto mapToContentfulArticleDto(CDAEntry entry) {
        ContentfulArticleDto article = new ContentfulArticleDto();
        ContentfulArticleDto.Fields fields = new ContentfulArticleDto.Fields();

        fields.setHeadTitle(entry.getField("headTitle"));
        fields.setShortDescription(entry.getField("shortDescription"));
        fields.setImportance(((Double) entry.getField("importance")).intValue());
        fields.setHeadAltImg(entry.getField("headAltImg"));
        fields.setSpecificTitle(entry.getField("specificTitle"));
        fields.setDescription(entry.getField("description"));
        fields.setEmployeeId(((Double) entry.getField("employeeId")).intValue());


        CDAAsset headImgSrcAsset = entry.getField("headImgSrc");
        if (headImgSrcAsset != null) {
            ContentfulArticleDto.Fields.Sys headImgSrc = new ContentfulArticleDto.Fields.Sys();
            headImgSrc.setId(headImgSrcAsset.id());
            fields.setHeadImgSrc(headImgSrc);
        }

        List<CDAAsset> imgSrcList = entry.getField("imgSrc");
        if (imgSrcList != null) {
            List<ContentfulArticleDto.Fields.Sys> imgSrcDtos = new ArrayList<>();
            for (CDAAsset imgEntry : imgSrcList) {
                ContentfulArticleDto.Fields.Sys imgSrc = new ContentfulArticleDto.Fields.Sys();
                imgSrc.setId(imgEntry.id());
                imgSrcDtos.add(imgSrc);
            }
            fields.setImgSrcList(imgSrcDtos);
        }

        article.setFields(fields);

        ContentfulArticleDto.Sys sys = new ContentfulArticleDto.Sys();
        sys.setId(entry.id());
        article.setSys(sys);

        return article;
    }
}