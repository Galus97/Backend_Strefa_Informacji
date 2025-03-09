package pl.strefainformacji.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidEmployee() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("secretPassword")
                .enabled(true)
                .emailCode("ABC123")
                .build();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertTrue(violations.isEmpty(), "Employee object should be valid");
    }

    @Test
    void testInvalidEmployee_shortFirstName() {
        Employee employee = Employee.builder()
                .firstName("Jo")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("secretPassword")
                .enabled(true)
                .emailCode("ABC123")
                .build();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty(), "First name shorter than 3 characters should trigger a validation error");
    }

    @Test
    void testInvalidEmployee_shortLastName() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Do")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("secretPassword")
                .enabled(true)
                .emailCode("ABC123")
                .build();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty(), "Last name shorter than 3 characters should trigger a validation error");
    }

    @Test
    void testInvalidEmployee_invalidEmail() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("invalid-email")
                .username("johndoe")
                .password("secretPassword")
                .enabled(true)
                .emailCode("ABC123")
                .build();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty(), "Invalid email should trigger a validation error");
    }

    @Test
    void testInvalidEmployee_shortUsername() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("jo")
                .password("secretPassword")
                .enabled(true)
                .emailCode("ABC123")
                .build();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty(), "Username shorter than 3 characters should trigger a validation error");
    }

    @Test
    void testInvalidEmployee_shortPassword() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("1234")
                .enabled(true)
                .emailCode("ABC123")
                .build();

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertFalse(violations.isEmpty(), "Password shorter than 5 characters should trigger a validation error");
    }
}
