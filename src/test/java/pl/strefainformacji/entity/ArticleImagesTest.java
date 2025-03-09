package pl.strefainformacji.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArticleImagesTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidArticleImages() {
        ArticleImages articleImages = ArticleImages.builder()
                .imgSrc("image.jpg")
                .altImg("Alternative image")
                .build();

        Set<ConstraintViolation<ArticleImages>> violations = validator.validate(articleImages);
        assertTrue(violations.isEmpty(), "ArticleImages object should be valid");
    }

    @Test
    void testInvalidArticleImages_emptyImgSrc() {
        ArticleImages articleImages = ArticleImages.builder()
                .imgSrc("")
                .altImg("Alternative image")
                .build();

        Set<ConstraintViolation<ArticleImages>> violations = validator.validate(articleImages);
        assertFalse(violations.isEmpty(), "Empty imgSrc should trigger a validation error");
    }

    @Test
    void testInvalidArticleImages_emptyAltImg() {
        ArticleImages articleImages = ArticleImages.builder()
                .imgSrc("image.jpg")
                .altImg("")
                .build();

        Set<ConstraintViolation<ArticleImages>> violations = validator.validate(articleImages);
        assertFalse(violations.isEmpty(), "Empty altImg should trigger a validation error");
    }

    @Test
    void testConstructorWithImgSrcAndAltImg() {
        ArticleImages articleImages = new ArticleImages("image.jpg", "Alternative image");
        assertEquals("image.jpg", articleImages.getImgSrc());
        assertEquals("Alternative image", articleImages.getAltImg());
    }
}
