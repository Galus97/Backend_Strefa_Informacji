package pl.strefainformacji.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleInformationTest {
    private ArticleInformation articleInformation;

    @BeforeEach
    public void setUp() {
        articleInformation = new ArticleInformation();
    }

    @Test
    public void testId() {
        assertNull(articleInformation.getId());
        Long id = 1L;
        articleInformation.setId(id);
        assertEquals(id, articleInformation.getId());
    }

    @Test
    public void testTitle() {
        assertNull(articleInformation.getTitle());
        String title = "Tytuł artykułu";
        articleInformation.setTitle(title);
        assertEquals(title, articleInformation.getTitle());
    }

    @Test
    public void testShortDescription() {
        assertNull(articleInformation.getShortDescription());
        String shortDescription = "Krótki opis artykułu";
        articleInformation.setShortDescription(shortDescription);
        assertEquals(shortDescription, articleInformation.getShortDescription());
    }

    @Test
    public void testImportance() {
        assertEquals(0, articleInformation.getImportance());
        int importance = 5;
        articleInformation.setImportance(importance);
        assertEquals(importance, articleInformation.getImportance());
    }

    @Test
    public void testImgSrc() {
        assertNull(articleInformation.getImgSrc());
        String imgSrc = "sciezka_obrazka.jpg";
        articleInformation.setImgSrc(imgSrc);
        assertEquals(imgSrc, articleInformation.getImgSrc());
    }

    @Test
    public void testAltImg() {
        assertNull(articleInformation.getAltImg());
        String altImg = "Opis alternatywny obrazka";
        articleInformation.setAltImg(altImg);
        assertEquals(altImg, articleInformation.getAltImg());
    }
}