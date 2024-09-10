package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateEnable_EmployeeExists() {
        Long employeeId = 1L;
        boolean enabled = true;
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));

        employeeService.updateEnable(employeeId, enabled);

        verify(employeeRepository, times(1)).updateEnabledByEmployeeId(employeeId, enabled);
    }

    @Test
    void testUpdateEnable_EmployeeDoesNotExist() {
        Long employeeId = 1L;
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.empty());

        employeeService.updateEnable(employeeId, true);

        verify(employeeRepository, never()).updateEnabledByEmployeeId(anyLong(), anyBoolean());
    }

    @Test
    void testIsEnabledById_EmployeeExists() {
        Long employeeId = 1L;
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.isEnabledById(employeeId)).thenReturn(true);

        boolean isEnabled = employeeService.isEnabledById(employeeId);

        assertTrue(isEnabled);
        verify(employeeRepository, times(1)).isEnabledById(employeeId);
    }

    @Test
    void testIsEnabledById_EmployeeDoesNotExist() {
        Long employeeId = 1L;
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> employeeService.isEnabledById(employeeId));
        verify(employeeRepository, never()).isEnabledById(anyLong());
    }

    @Test
    void testFindByEmployeeId_EmployeeExists() {
        Long employeeId = 1L;
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.findByEmployeeId(employeeId);

        assertTrue(result.isPresent());
        assertEquals(employee, result.get());
        verify(employeeRepository, times(2)).findByEmployeeId(employeeId);
    }

    @Test
    void testFindByEmployeeId_EmployeeDoesNotExist() {
        Long employeeId = 1L;
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.empty());


        assertThrows(NullPointerException.class, () -> employeeService.findByEmployeeId(employeeId));

        verify(employeeRepository, times(1)).findByEmployeeId(employeeId);
    }

    @Test
    void testUpdateEmailCode_Success() {
        Long employeeId = 1L;
        String emailCode = "newCode";
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));

        employeeService.updateEmailCode(employeeId, emailCode);

        verify(employeeRepository, times(1)).updateEmailCodeByEmployeeId(employeeId, emailCode);
    }

    @Test
    void testUpdateEmailCode_InvalidEmailCode() {
        Long employeeId = 1L;
        String invalidEmailCode = "  ";
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));

        employeeService.updateEmailCode(employeeId, invalidEmailCode);

        verify(employeeRepository, never()).updateEmailCodeByEmployeeId(anyLong(), anyString());
    }

    @Test
    void testChangePassword_Success() {
        Long employeeId = 1L;
        String newPassword = "newPassword";
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));

        employeeService.changePassword(employeeId, newPassword);

        verify(employeeRepository, times(1)).changePasswordByEmployeeId(employeeId, newPassword);
    }

    @Test
    void testChangePassword_InvalidPassword() {
        Long employeeId = 1L;
        String invalidPassword = "";
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(Optional.of(employee));

        employeeService.changePassword(employeeId, invalidPassword);

        verify(employeeRepository, never()).changePasswordByEmployeeId(anyLong(), anyString());
    }
}