package pl.strefainformacji.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class SpecificArticleTest {

    private SpecificArticle specificArticle;

    @Autowired
    private Validator validator;

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
    public void testDescriptionMinSize(){
        System.out.println("Test start");
        try{
            specificArticle.setDescription("Short");
            fail("Expected ValidationException not thrown");
        } catch (ValidationException exception){
            assertTrue(exception.getMessage().contains("description"));
        }
        System.out.println("Test end");
    }

    @Test
    public void testDescriptionMinSize2() {
        specificArticle.setDescription("Short");
        ConstraintViolation<SpecificArticle> violation = validator.validate(specificArticle).stream()
                .filter(v -> "description".equals(v.getPropertyPath().toString()))
                .findFirst()
                .orElse(null);

        assertNotNull(violation);
        assertEquals("size must be between 30 and 2147483647", violation.getMessage());
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