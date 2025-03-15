package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.strefainformacji.entity.ArticleImages;
import pl.strefainformacji.entity.ArticleInformation;
import pl.strefainformacji.entity.SpecificArticle;
import pl.strefainformacji.model.ArticleDto;
import pl.strefainformacji.repository.ArticleImagesRepository;
import pl.strefainformacji.repository.ArticleInformationRepository;
import pl.strefainformacji.repository.SpecificArticleRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleInformationRepository articleInformationRepository;

    @Mock
    private SpecificArticleRepository specificArticleRepository;

    @Mock
    private ArticleImagesRepository articleImagesRepository;

    @InjectMocks
    private ArticleService articleService;

    private ArticleDto articleDto;
    private ArticleInformation articleInformation;
    private SpecificArticle specificArticle;
    private List<ArticleImages> images;

    @BeforeEach
    void setUp() {
        articleInformation = new ArticleInformation();
        articleInformation.setContentfulId("11111");

        specificArticle = new SpecificArticle();
        specificArticle.setTitle("Test Title");

        ArticleImages image1 = new ArticleImages();
        image1.setImgSrc("img1");
        image1.setAltImg("alt1");

        ArticleImages image2 = new ArticleImages();
        image2.setImgSrc("img2");
        image2.setAltImg("alt2");

        images = Arrays.asList(image1, image2);

        articleDto = new ArticleDto();
        articleDto.setArticleInformation(articleInformation);
        articleDto.setSpecificArticle(specificArticle);
        articleDto.setImages(images);
    }

    @Test
    void saveFullArticle_SavesAllDataSuccessfully() {
        // Arrange
        when(articleInformationRepository.save(any(ArticleInformation.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(specificArticleRepository.save(any(SpecificArticle.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(articleImagesRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        articleService.saveFullArticle(articleDto);

        // Assert
        ArgumentCaptor<ArticleInformation> articleInfoCaptor = ArgumentCaptor.forClass(ArticleInformation.class);
        verify(articleInformationRepository, times(1)).save(articleInfoCaptor.capture());
        ArticleInformation savedInfo = articleInfoCaptor.getValue();
        assertNotNull(savedInfo);
        assertEquals("11111", savedInfo.getContentfulId());

        ArgumentCaptor<SpecificArticle> specificArticleCaptor = ArgumentCaptor.forClass(SpecificArticle.class);
        verify(specificArticleRepository, times(1)).save(specificArticleCaptor.capture());
        SpecificArticle savedSpecific = specificArticleCaptor.getValue();
        assertNotNull(savedSpecific);
        assertEquals("Test Title", savedSpecific.getTitle());

        assertEquals(savedInfo, savedSpecific.getArticleInformation());
        
        verify(articleImagesRepository, times(images.size())).save(any());
    }
}