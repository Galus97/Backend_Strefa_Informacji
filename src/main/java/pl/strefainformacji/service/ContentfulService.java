package pl.strefainformacji.service;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.exception.ContentfulIntegrationException;
import pl.strefainformacji.webclient.contentful.ContentfulClient;
import pl.strefainformacji.webclient.contentful.dto.ContentfulArticleDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentfulService {
    private final CDAClient client;
    private static final String ARTICLE_TYPE = "article";
    private static final String CREATED_AT_DESC = "-sys.createdAt";
    private static final int DEFAULT_LIMIT = 10;

    private final MessageService messageService;

    public ContentfulService(ContentfulClient contentfulClient, MessageService messageService) {
        this.client = contentfulClient.createClient();
        this.messageService = messageService;
    }

    public List<String> getAllArticlesIds() {
        return client.fetch(CDAEntry.class)
                .withContentType(ARTICLE_TYPE)
                .orderBy(CREATED_AT_DESC)
                .limit(DEFAULT_LIMIT)
                .all()
                .items()
                .stream()
                .map(CDAResource::id)
                .collect(Collectors.toList());
    }

    public ContentfulArticleDto getArticleById(String id) {
        try {
            CDAEntry entry = client.fetch(CDAEntry.class).one(id);
            return entry != null ? mapToContentfulArticleDto(entry) : null;
        } catch (Exception e) {
            throw new ContentfulIntegrationException(messageService.getMessage("error.contentful", id), e);
        }
    }

    private ContentfulArticleDto mapToContentfulArticleDto(CDAEntry entry) {
        ContentfulArticleDto.Fields fields = new ContentfulArticleDto.Fields();
        fields.setHeadTitle(entry.getField("headTitle"));
        fields.setShortDescription(entry.getField("shortDescription"));
        fields.setImportance(((Double) entry.getField("importance")).intValue());
        fields.setEmployeeId(getIntField(entry, "employeeId"));
        fields.setHeadAltImg(getStringField(entry, "headAltImg"));
        fields.setSpecificTitle(getStringField(entry, "specificTitle"));
        fields.setDescription(getStringField(entry, "description"));

        CDAAsset headImgSrcAsset = entry.getField("headImgSrc");
        fields.setHeadImgSrc(createImageSys(headImgSrcAsset));

        handleAssets(entry, fields);

        return new ContentfulArticleDto()
                .setSys(new ContentfulArticleDto.Sys().setId(entry.id()))
                .setFields(fields);
    }

    private ContentfulArticleDto.Fields.Sys createImageSys(CDAAsset asset) {
        return new ContentfulArticleDto.Fields.Sys().setId(asset != null ? asset.id() : null);
    }

    private void handleAssets(CDAEntry entry, ContentfulArticleDto.Fields fields) {
        List<CDAAsset> assets = entry.getField("imgSrc");
        if (assets != null) {
            List<ContentfulArticleDto.Fields.Sys> imgSrcDtos = new ArrayList<>();
            List<String> altImgList = new ArrayList<>();

            assets.forEach(asset -> {
                imgSrcDtos.add(new ContentfulArticleDto.Fields.Sys().setId(asset.id()));
                altImgList.add(Optional.ofNullable(asset.getField("description"))
                        .map(Object::toString)
                        .orElse(""));
            });

            fields.setImgSrcList(imgSrcDtos);
            fields.setAltImgList(altImgList);
        }
    }

    private String getStringField(CDAEntry entry, String fieldName) {
        return Optional.ofNullable(entry.getField(fieldName))
                .map(Object::toString)
                .orElse(null);
    }

    private int getIntField(CDAEntry entry, String fieldName) {
        return Optional.ofNullable(entry.getField(fieldName))
                .map(Double.class::cast)
                .map(Double::intValue)
                .orElse(0);
    }
}