package pl.strefainformacji.entity;

import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

public class SpecificArticleTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidSpecificArticle() {
        SpecificArticle specificArticle = new SpecificArticle();
        specificArticle.setDescription("Valid description with more than 30 characters");
        specificArticle.setImgSrc("valid-img-src");
        specificArticle.setAltImg("valid-alt-img");

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);

        assert(violations.isEmpty());
    }

    @Test
    public void testInvalidDescriptionSize() {
        SpecificArticle specificArticle = new SpecificArticle();
        specificArticle.setDescription("Short");

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);

        assert(violations.size() == 1);
    }

    @Test
    public void testBlankImgSrc() {
        SpecificArticle specificArticle = new SpecificArticle();
        specificArticle.setDescription("Valid description");
        specificArticle.setImgSrc("");  // Blank imgSrc

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);

        assert(violations.size() == 1);
    }

    @Test
    public void testBlankAltImg() {
        SpecificArticle specificArticle = new SpecificArticle();
        specificArticle.setDescription("Valid description");
        specificArticle.setImgSrc("valid-img-src");
        specificArticle.setAltImg("");  // Blank altImg

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);

        assert(violations.size() == 1);
    }
}