package pl.strefainformacji.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArticleInformationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidArticleInformation() {
        ArticleInformation articleInformation = ArticleInformation.builder()
                .contentfulId("12345")
                .title("Valid Title")
                .shortDescription("This is a valid short description")
                .importance(5)
                .imgSrc("image.jpg")
                .altImg("Alternative image")
                .localDateTime(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInformation);
        assertTrue(violations.isEmpty(), "ArticleInformation object should be valid");
    }

    @Test
    void testInvalidArticleInformation_shortTitle() {
        ArticleInformation articleInformation = ArticleInformation.builder()
                .contentfulId("12345")
                .title("No")  // too short, min. 3 characters
                .shortDescription("This is a valid short description")
                .importance(5)
                .imgSrc("image.jpg")
                .altImg("Alternative image")
                .localDateTime(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInformation);
        assertFalse(violations.isEmpty(), "Title shorter than 3 characters should trigger a validation error");
    }

    @Test
    void testInvalidArticleInformation_invalidImportance() {
        // importance less than 1
        ArticleInformation articleInformationLow = ArticleInformation.builder()
                .contentfulId("12345")
                .title("Valid Title")
                .shortDescription("This is a valid short description")
                .importance(0)
                .imgSrc("image.jpg")
                .altImg("Alternative image")
                .localDateTime(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<ArticleInformation>> violationsLow = validator.validate(articleInformationLow);
        assertFalse(violationsLow.isEmpty(), "Importance below 1 should be invalid");

        // importance greater than 10
        ArticleInformation articleInformationHigh = ArticleInformation.builder()
                .contentfulId("12345")
                .title("Valid Title")
                .shortDescription("This is a valid short description")
                .importance(11)
                .imgSrc("image.jpg")
                .altImg("Alternative image")
                .localDateTime(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<ArticleInformation>> violationsHigh = validator.validate(articleInformationHigh);
        assertFalse(violationsHigh.isEmpty(), "Importance above 10 should be invalid");
    }

    @Test
    void testInvalidArticleInformation_emptyContentfulId() {
        ArticleInformation articleInformation = ArticleInformation.builder()
                .contentfulId("")  // empty contentfulId - @Size(min = 5)
                .title("Valid Title")
                .shortDescription("This is a valid short description")
                .importance(5)
                .imgSrc("image.jpg")
                .altImg("Alternative image")
                .localDateTime(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInformation);
        assertFalse(violations.isEmpty(), "Empty contentfulId should trigger a validation error");
    }
}
