package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
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
        articleInformation.setContentfulId("dummyId"); // przykładowe ustawienie

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
}