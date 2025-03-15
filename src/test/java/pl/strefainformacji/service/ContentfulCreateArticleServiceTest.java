package pl.strefainformacji.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.webclient.contentful.dto.ContentfulArticleDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContentfulCreateArticleServiceTest {

    @Mock
    private ArticleInformationService articleInformationService;

    @Mock
    private SpecificArticleService specificArticleService;

    @Mock
    private ArticleImagesService articleImagesService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ContentfulService contentfulService;

    @InjectMocks
    private ContentfulCreateArticleService contentfulCreateArticleService;

    @Test
    void importNewArticles_NoExistingArticles_ShouldImportAll() {
        // Arrange
        when(articleInformationService.findAllContentfulIds()).thenReturn(Collections.emptyList());
        List<String> allContentfulIds = Arrays.asList("id1");
        when(contentfulService.getAllArticlesIds()).thenReturn(allContentfulIds);

        ContentfulArticleDto dto = createDummyDto("id1");
        when(contentfulService.getArticleById("id1")).thenReturn(dto);

        Employee employee = new Employee();
        employee.setEmployeeId(10L);
        when(employeeService.getEmployee(dto.getFields().getEmployeeId().longValue())).thenReturn(employee);

        // Act
        contentfulCreateArticleService.importNewArticles();

        // Assert
        verify(articleInformationService, times(1)).saveArticleInformation(any(ArticleInformation.class));
        verify(specificArticleService, times(1)).saveSpecificArticle(any(SpecificArticle.class));
        verify(articleImagesService, times(dto.getFields().getImgSrcList().size())).saveArticleImages(any(ArticleImages.class));
    }

    @Test
    void importNewArticles_ExistingArticles_ShouldImportOnlyNew() {
        // Arrange
        when(articleInformationService.findAllContentfulIds()).thenReturn(Arrays.asList("id1"));

        List<String> allContentfulIds = Arrays.asList("id1", "id2");
        when(contentfulService.getAllArticlesIds()).thenReturn(allContentfulIds);

        ContentfulArticleDto dto = createDummyDto("id2");
        when(contentfulService.getArticleById("id2")).thenReturn(dto);

        Employee employee = new Employee();
        employee.setEmployeeId(20L);
        when(employeeService.getEmployee(dto.getFields().getEmployeeId().longValue())).thenReturn(employee);

        // Act
        contentfulCreateArticleService.importNewArticles();

        // Assert
        verify(articleInformationService, times(1)).saveArticleInformation(any(ArticleInformation.class));
        verify(specificArticleService, times(1)).saveSpecificArticle(any(SpecificArticle.class));
        verify(articleImagesService, times(dto.getFields().getImgSrcList().size())).saveArticleImages(any(ArticleImages.class));
    }

    private ContentfulArticleDto createDummyDto(String id) {
        ContentfulArticleDto dto = new ContentfulArticleDto();


        ContentfulArticleDto.Sys sys = new ContentfulArticleDto.Sys();
        sys.setId(id);
        dto.setSys(sys);


        ContentfulArticleDto.Fields fields = new ContentfulArticleDto.Fields();
        fields.setHeadTitle("Test Title");
        fields.setShortDescription("Test Short Description");
        fields.setImportance(1);

        ContentfulArticleDto.Fields.Sys headImgSrc = new ContentfulArticleDto.Fields.Sys();
        headImgSrc.setId("headImg1");
        fields.setHeadImgSrc(headImgSrc);

        List<ContentfulArticleDto.Fields.Sys> imgSrcList = new ArrayList<>();
        ContentfulArticleDto.Fields.Sys imgSys1 = new ContentfulArticleDto.Fields.Sys();
        imgSys1.setId("img1");
        imgSrcList.add(imgSys1);
        fields.setImgSrcList(imgSrcList);

        List<String> altImgList = new ArrayList<>();
        altImgList.add("alt1");
        fields.setAltImgList(altImgList);

        fields.setEmployeeId(5);
        fields.setHeadAltImg("Test Head Alt Img");
        fields.setSpecificTitle("Specific Title");
        fields.setDescription("Detailed Description");

        dto.setFields(fields);
        return dto;
    }
}
