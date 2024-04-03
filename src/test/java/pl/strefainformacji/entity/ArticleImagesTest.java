package pl.strefainformacji.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleImagesTest {

    private Validator validator;
    private ArticleImages articleImages;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        articleImages = new ArticleImages();
        articleImages.setImgSrc("valid-img-src");
        articleImages.setAltImg("valid-alt-img");
    }

    @Test
    public void testValidArticleImages(){
        Set<ConstraintViolation<ArticleImages>> violations = validator.validate(articleImages);

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidImgSrc(){
        articleImages.setImgSrc("");

        Set<ConstraintViolation<ArticleImages>> violations = validator.validate(articleImages);

        assertEquals(1, violations.size());
    }

    @Test
    public void testInvalidAltImg(){
        articleImages.setAltImg("");

        Set<ConstraintViolation<ArticleImages>> violations = validator.validate(articleImages);

        assertEquals(1, violations.size());
    }

}