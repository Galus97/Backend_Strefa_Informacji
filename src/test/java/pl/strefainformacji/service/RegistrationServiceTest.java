package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ValidationException;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNewEmployeeRegistration_Success() throws ValidationException {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";
        when(employee.getUsername()).thenReturn("username");
        when(employee.getEmail()).thenReturn("email@example.com");
        when(employeeRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(employee.getPassword()).thenReturn(rawPassword);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = registrationService.newEmployeeRegistration(employee);

        assertNotNull(result);
        verify(employee, times(1)).setPassword(encodedPassword);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testNewEmployeeRegistration_UsernameAlreadyExists() {
        when(employee.getUsername()).thenReturn("username");
        when(employeeRepository.findByUsername(anyString())).thenReturn(Optional.of(employee));
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            registrationService.newEmployeeRegistration(employee);
        });

        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("existUsername", "Użytkownik z taką nazwą już istnieje. Wpisz inną nazwę użytkownika");

        assertEquals(expectedErrors, exception.getValidationErrors());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testNewEmployeeRegistration_EmailAlreadyExists() {
        when(employee.getUsername()).thenReturn("username");
        when(employee.getEmail()).thenReturn("email@example.com");
        when(employeeRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(employee));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            registrationService.newEmployeeRegistration(employee);
        });

        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("existEmail", "Ten adres email jest już używany. Wpisz inny adres email");

        assertEquals(expectedErrors, exception.getValidationErrors());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testNewEmployeeRegistration_UsernameAndEmailAlreadyExist() {
        when(employee.getUsername()).thenReturn("username");
        when(employee.getEmail()).thenReturn("email@example.com");
        when(employeeRepository.findByUsername(anyString())).thenReturn(Optional.of(employee));
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(employee));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            registrationService.newEmployeeRegistration(employee);
        });

        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("existUsername", "Użytkownik z taką nazwą już istnieje. Wpisz inną nazwę użytkownika");
        expectedErrors.put("existEmail", "Ten adres email jest już używany. Wpisz inny adres email");

        assertEquals(expectedErrors, exception.getValidationErrors());
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}