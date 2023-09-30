package pl.strefainformacji.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class ArticleInformationTest {
    private ArticleInformation articleInformation;

    @BeforeEach
    public void setUp() {
        articleInformation = new ArticleInformation();
    }

    @Test
    public void testId() {
        assertNull(articleInformation.getId());
        articleInformation.setId(1L);
        assertEquals(1L, articleInformation.getId());
    }

    @Test
    public void testTitle() {
        assertNull(articleInformation.getTitle());
        articleInformation.setTitle("Article Title 1");
        assertEquals("Article Title 1", articleInformation.getTitle());
    }

    @Test
    public void testShortDescription() {
        assertNull(articleInformation.getShortDescription());
        articleInformation.setShortDescription("Short description of article 1");
        assertEquals("Short description of article 1", articleInformation.getShortDescription());
    }

    @Test
    public void testImportance() {
        assertEquals(0, articleInformation.getImportance());
        articleInformation.setImportance(5);
        assertEquals(5, articleInformation.getImportance());
    }

    @Test
    public void testImgSrc() {
        assertNull(articleInformation.getImgSrc());
        articleInformation.setImgSrc("path/to/image1.jpg");
        assertEquals("path/to/image1.jpg", articleInformation.getImgSrc());
    }

    @Test
    public void testAltImg() {
        assertNull(articleInformation.getAltImg());
        articleInformation.setAltImg("Image description 1");
        assertEquals("Image description 1", articleInformation.getAltImg());
    }
}