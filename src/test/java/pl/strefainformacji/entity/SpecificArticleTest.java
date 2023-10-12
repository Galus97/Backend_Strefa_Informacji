package pl.strefainformacji.entity;

import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecificArticleTest {

    private Validator validator;
    private SpecificArticle specificArticle;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        specificArticle = new SpecificArticle();
        specificArticle.setDescription("Valid description with more than 30 characters");
        specificArticle.setImgSrc("valid-img-src");
        specificArticle.setAltImg("valid-alt-img");
    }

    @Test
    public void testValidSpecificArticle() {

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidDescriptionSize() {

        specificArticle.setDescription("Short");

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);

        assertEquals(1, violations.size());
    }

    @Test
    public void testBlankImgSrc() {
        specificArticle.setImgSrc("");

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);

        assertEquals(1, violations.size());
    }

    @Test
    public void testBlankAltImg() {
        specificArticle.setAltImg("");

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);

        assertEquals(1, violations.size());
    }
}