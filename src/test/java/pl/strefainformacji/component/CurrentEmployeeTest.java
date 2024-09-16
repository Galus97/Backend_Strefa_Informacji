package pl.strefainformacji.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.strefainformacji.entity.Employee;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CurrentEmployeeTest {

    private Employee employee;
    private CurrentEmployee currentEmployee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setUsername("testuser");
        employee.setEmail("testuser@example.com");
        employee.setPassword("password");

        Collection<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        currentEmployee = new CurrentEmployee("testuser", "password", authorities, employee);
    }

    @Test
    void testGetEmployee() {
        assertNotNull(currentEmployee.getEmployee());
        assertEquals(employee, currentEmployee.getEmployee());
    }

    @Test
    void testGetUsername() {
        assertEquals("testuser", currentEmployee.getUsername());
    }

    @Test
    void testGetPassword() {
        assertEquals("password", currentEmployee.getPassword());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = currentEmployee.getAuthorities();
        assertNotNull(authorities);
        assertEquals(2, authorities.size());
    }

    @Test
    void testEmployeeFields() {
        assertEquals(1L, currentEmployee.getEmployee().getEmployeeId());
        assertEquals("testuser", currentEmployee.getEmployee().getUsername());
        assertEquals("testuser@example.com", currentEmployee.getEmployee().getEmail());
    }
}