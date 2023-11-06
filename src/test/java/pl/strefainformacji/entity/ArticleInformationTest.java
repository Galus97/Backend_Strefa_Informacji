package pl.strefainformacji.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.validation.*;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleInformationTest {

    private Validator validator;
    private ArticleInformation articleInfo;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        articleInfo = new ArticleInformation();
        articleInfo.setTitle("Valid Title");
        articleInfo.setShortDescription("Valid short description with more than 10 characters");
        articleInfo.setImportance(1);
        articleInfo.setImgSrc("valid-img-src");
        articleInfo.setAltImg("valid-alt-img");
    }

    @Test
    public void testValidArticleInformation() {

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidTitleSize() {
        articleInfo.setTitle("S");

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        assertEquals(1, violations.size());
    }

    @Test
    public void testBlankShortDescription() {
        articleInfo.setShortDescription("");  // Blank shortDescription

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        assertEquals(1, violations.size());
    }

    @Test
    public void testNonPositiveImportance() {

        articleInfo.setImportance(-2);

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        assertEquals(1, violations.size());

    }

    @Test
    public void testToBigImportance() {

        articleInfo.setImportance(20);

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        assertEquals(1, violations.size());

    }

    @Test
    public void testBlankImgSrc(){
        articleInfo.setImgSrc("");

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        assertEquals(1, violations.size());
    }

    @Test
    public void testBlankAltImg(){
        articleInfo.setAltImg("");

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        assertEquals(1, violations.size());
    }
}