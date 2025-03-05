package pl.strefainformacji.service;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import org.springframework.stereotype.Service;
import pl.strefainformacji.webclient.contentful.ContentfulClient;
import pl.strefainformacji.webclient.contentful.dto.ContentfulArticleDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentfulService {
    private final CDAClient client;
    private static final String CONTENT_TYPE = "content_type";
    private static final String CREATED_AT_DESC = "-sys.createdAt";
    private static final int DEFAULT_LIMIT = 10;

    public ContentfulService(ContentfulClient contentfulClient2) {
        this.client = contentfulClient2.createClient();
    }

    public List<String> getAllArticlesIds() {
        return client.fetch(CDAEntry.class)
                .withContentType(CONTENT_TYPE)
                .orderBy(CREATED_AT_DESC)
                .limit(DEFAULT_LIMIT)
                .all()
                .items()
                .stream()
                .map(CDAResource::id)
                .collect(Collectors.toList());
    }

    public ContentfulArticleDto getArticleById(String id) {
        CDAEntry entry = client.fetch(CDAEntry.class).one(id);
        if (entry == null) {
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
            List<String> altImgList = new ArrayList<>();
            for (CDAAsset imgEntry : imgSrcList) {
                ContentfulArticleDto.Fields.Sys imgSrc = new ContentfulArticleDto.Fields.Sys();
                String assetId = imgEntry.id();
                imgSrc.setId(assetId);
                imgSrcDtos.add(imgSrc);

                CDAAsset asset = client.fetch(CDAAsset.class).one(assetId);
                String altImg = asset.getField("description").toString();
                if (altImg != null) {
                    altImgList.add(altImg);
                } else {
                    altImgList.add("");
                }
            }
            fields.setImgSrcList(imgSrcDtos);
            fields.setAltImgList(altImgList);
        }

        article.setFields(fields);

        ContentfulArticleDto.Sys sys = new ContentfulArticleDto.Sys();
        sys.setId(entry.id());
        article.setSys(sys);

        return article;
    }
}