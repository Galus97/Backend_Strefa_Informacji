package pl.strefainformacji.exception;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationExceptionTest {

    @Test
    void testValidationExceptionStoresErrors() {
        Map<String, String> validationErrors = Map.of(
                "username", "Username already exists",
                "email", "Email is invalid"
        );

        ValidationException exception = new ValidationException(validationErrors);

        assertThat(exception.getValidationErrors()).isEqualTo(validationErrors);
    }

    @Test
    void testValidationExceptionThrowsCorrectly() {
        Map<String, String> validationErrors = Map.of(
                "username", "Username already exists"
        );

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            throw new ValidationException(validationErrors);
        });

        assertThat(exception.getValidationErrors()).containsEntry("username", "Username already exists");
    }

    @Test
    void testValidationExceptionWithEmptyErrors() {
        Map<String, String> validationErrors = Map.of();

        ValidationException exception = new ValidationException(validationErrors);

        assertThat(exception.getValidationErrors()).isEmpty();
    }
}