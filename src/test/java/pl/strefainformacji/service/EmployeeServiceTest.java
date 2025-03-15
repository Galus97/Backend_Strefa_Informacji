package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.strefainformacji.component.ErrorMessages;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.EmployeeNotFoundException;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private final Long employeeId = 1L;

    @BeforeEach
    void setUp() {
        employee = new Employee();
    }

    @Test
    void givenExistingEmployee_whenGetEmployee_thenReturnEmployee() {
        // Arrange
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        Employee foundEmployee = employeeService.getEmployee(employeeId);

        // Assert
        assertThat(foundEmployee).isEqualTo(employee);
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void givenNonExistingEmployee_whenGetEmployee_thenThrowException() {
        // Arrange
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        when(messageService.getMessage(ErrorMessages.EMPLOYEE_NOT_FOUND, employeeId)).thenReturn("Employee not found");

        // Act & Assert
        assertThatThrownBy(() -> employeeService.getEmployee(employeeId))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee not found");
    }

    @Test
    void givenExistingEmployee_whenDeleteEmployee_thenDeleteSuccessfully() {
        // Arrange
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        employeeService.deleteEmployee(employeeId);

        // Assert
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void givenNonExistingEmployee_whenDeleteEmployee_thenThrowException() {
        // Arrange
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        when(messageService.getMessage(ErrorMessages.EMPLOYEE_NOT_FOUND, employeeId)).thenReturn("Employee not found");

        // Act & Assert
        assertThatThrownBy(() -> employeeService.deleteEmployee(employeeId))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee not found");
    }

    @Test
    void givenExistingEmployee_whenUpdateEnable_thenUpdateSuccessfully() {
        // Arrange
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        // Act
        employeeService.updateEnable(employeeId, true);

        // Assert
        verify(employeeRepository, times(1)).updateEnabledByEmployeeId(employeeId, true);
    }

    @Test
    void givenNonExistingEmployee_whenUpdateEnable_thenThrowException() {
        // Arrange
        when(employeeRepository.existsById(employeeId)).thenReturn(false);
        when(messageService.getMessage(ErrorMessages.EMPLOYEE_NOT_FOUND, employeeId)).thenReturn("Employee not found");

        // Act & Assert
        assertThatThrownBy(() -> employeeService.updateEnable(employeeId, true))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee not found");
    }

    @Test
    void givenExistingEmployee_whenChangePassword_thenUpdatePassword() {
        // Arrange
        String newPassword = "newSecurePassword";
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        // Act
        employeeService.changePassword(employeeId, newPassword);

        // Assert
        verify(employeeRepository, times(1)).changePasswordByEmployeeId(employeeId, newPassword);
    }

    @Test
    void givenInvalidPassword_whenChangePassword_thenDoNothing() {
        // Given
        Long employeeId = 1L;
        String invalidPassword = "  ";
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        // When
        employeeService.changePassword(employeeId, invalidPassword);

        // Then
        verify(employeeRepository, never()).changePasswordByEmployeeId(anyLong(), anyString());
    }

}
