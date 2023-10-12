package pl.strefainformacji.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.validation.*;
import java.util.Set;

public class ArticleInformationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidArticleInformation() {
        ArticleInformation articleInfo = new ArticleInformation();
        articleInfo.setTitle("Valid Title");
        articleInfo.setShortDescription("Valid short description with more than 10 characters");
        articleInfo.setImportance(1);
        articleInfo.setImgSrc("valid-img-src");
        articleInfo.setAltImg("valid-alt-img");

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        assert(violations.isEmpty());
    }

    @Test
    public void testInvalidTitleSize() {
        ArticleInformation articleInfo = new ArticleInformation();
        articleInfo.setTitle("S");

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        assert(violations.size() == 1);
    }

    @Test
    public void testBlankShortDescription() {
        ArticleInformation articleInfo = new ArticleInformation();
        articleInfo.setTitle("Valid Title");
        articleInfo.setShortDescription("");  // Blank shortDescription

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        // Expecting a violation due to @NotBlank constraint
        assert(violations.size() == 1);
    }

    @Test
    public void testNullImportance() {
        Integer nullNumber = null;
        ArticleInformation articleInfo = new ArticleInformation();
        articleInfo.setTitle("Valid Title");
        articleInfo.setShortDescription("Valid short description");
        articleInfo.setImportance(nullNumber);  // Null importance

        Set<ConstraintViolation<ArticleInformation>> violations = validator.validate(articleInfo);

        // Expecting a violation due to @NotNull constraint
        assert(violations.size() == 1);
    }
}