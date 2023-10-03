package pl.strefainformacji.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecificArticleTest {

    private SpecificArticle specificArticle;

    @BeforeEach
    public void setUp(){
        specificArticle = new SpecificArticle();
    }

    @Test
    public void testId(){
        assertNull(specificArticle.getId());
        specificArticle.setId(1L);
        assertEquals(1L, specificArticle.getId());
    }

    @Test
    public void testDescription(){
        assertNull(specificArticle.getDescription());
        specificArticle.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit.", specificArticle.getDescription());
    }


    @Test
    public void testImgSrc(){
        assertNull(specificArticle.getImgSrc());
        specificArticle.setImgSrc("path/to/image1.jpg");
        assertEquals("path/to/image1.jpg", specificArticle.getImgSrc());

    }

    @Test
    public void testAltImg() {
        assertNull(specificArticle.getAltImg());
        specificArticle.setAltImg("Image description 1");
        assertEquals("Image description 1", specificArticle.getAltImg());
    }

    @Test
    public void testArticleInformationId(){
        assertNull(specificArticle.getArticleInformation());
        ArticleInformation articleInformation = new ArticleInformation();
        articleInformation.setId(1L);
        specificArticle.setArticleInformation(articleInformation);
        assertEquals(1L, specificArticle.getArticleInformation().getId());
    }
}