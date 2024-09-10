package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomEmployeeDetailsServiceTest {

    @InjectMocks
    private CustomEmployeeDetailsService customEmployeeDetailsService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        String username = "testUser";
        when(employeeRepository.findByUsername(username)).thenReturn(Optional.of(employee));
        when(employee.getUsername()).thenReturn(username);
        when(employee.getPassword()).thenReturn("testPassword");

        CurrentEmployee currentEmployee = (CurrentEmployee) customEmployeeDetailsService.loadUserByUsername(username);

        assertNotNull(currentEmployee);
        assertEquals(username, currentEmployee.getUsername());
        assertEquals("testPassword", currentEmployee.getPassword());
        assertTrue(currentEmployee.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

        verify(employeeRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "unknownUser";
        when(employeeRepository.findByUsername(username)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            customEmployeeDetailsService.loadUserByUsername(username);
        });

        assertEquals("Employee not found with username: " + username, exception.getMessage());

        verify(employeeRepository, times(1)).findByUsername(username);
    }
}