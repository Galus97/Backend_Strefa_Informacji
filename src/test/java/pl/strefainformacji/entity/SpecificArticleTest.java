package pl.strefainformacji.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpecificArticleTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidSpecificArticle() {
        ArticleInformation articleInformation = ArticleInformation.builder()
                .contentfulId("12345")
                .title("Valid Title")
                .shortDescription("This is a valid short description")
                .importance(5)
                .imgSrc("image.jpg")
                .altImg("Alternative image")
                .localDateTime(java.time.LocalDateTime.now())
                .build();

        SpecificArticle specificArticle = SpecificArticle.builder()
                .title("Specific Title")
                .description("This is a valid description that has more than thirty characters.")
                .articleInformation(articleInformation)
                .articleImages(Collections.emptyList())
                .build();

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);
        assertTrue(violations.isEmpty(), "SpecificArticle object should be valid");
    }

    @Test
    void testInvalidSpecificArticle_shortTitle() {
        SpecificArticle specificArticle = SpecificArticle.builder()
                .title("No") // too short, min. 3 characters
                .description("This is a valid description that has more than thirty characters.")
                .build();

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);
        assertFalse(violations.isEmpty(), "Title shorter than 3 characters should trigger a validation error");
    }

    @Test
    void testInvalidSpecificArticle_shortDescription() {
        SpecificArticle specificArticle = SpecificArticle.builder()
                .title("Specific Title")
                .description("Too short") // less than 30 characters
                .build();

        Set<ConstraintViolation<SpecificArticle>> violations = validator.validate(specificArticle);
        assertFalse(violations.isEmpty(), "Description shorter than 30 characters should trigger a validation error");
    }
}
