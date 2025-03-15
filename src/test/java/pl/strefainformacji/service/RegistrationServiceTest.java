package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.strefainformacji.component.EmployeeValidator;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ValidationException;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmployeeValidator employeeValidator;

    @InjectMocks
    private RegistrationService registrationService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .employeeId(1L)
                .email("test@example.com")
                .password("password123")
                .build();
    }

    @Test
    void givenValidEmployee_whenNewEmployeeRegistration_thenReturnSavedEmployee() throws ValidationException {
        // Arrange
        when(employeeValidator.validate(employee)).thenReturn(Collections.emptyMap());
        when(passwordEncoder.encode(employee.getPassword())).thenReturn("encodedPassword");
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Employee registeredEmployee = registrationService.newEmployeeRegistration(employee);

        // Assert
        assertThat(registeredEmployee).isNotNull();
        assertThat(registeredEmployee.getEmployeeId()).isNull(); // Powinno być nadpisane na null
        assertThat(registeredEmployee.getPassword()).isEqualTo("encodedPassword");

        verify(employeeValidator, times(1)).validate(employee);
        verify(passwordEncoder, times(1)).encode("password123");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void givenInvalidEmployee_whenNewEmployeeRegistration_thenThrowValidationException() {
        // Arrange
        Map<String, String> validationErrors = Map.of("email", "Invalid email format");
        when(employeeValidator.validate(employee)).thenReturn(validationErrors);

        // Act & Assert
        assertThatThrownBy(() -> registrationService.newEmployeeRegistration(employee))
                .isInstanceOf(ValidationException.class)
                .extracting(e -> ((ValidationException) e).getValidationErrors())
                .isEqualTo(validationErrors);

        verify(employeeValidator, times(1)).validate(employee);
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(employeeRepository);
    }

}
