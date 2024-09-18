package pl.strefainformacji.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import pl.strefainformacji.entity.Employee;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        // Przygotowanie przykładowego obiektu Employee
        employee = new Employee();
        employee.setUsername("john.doe");
        employee.setEmail("john.doe@example.com");
        employee.setEmployeeId(1L);
        employee.setEnabled(true);
        employee.setPassword("password123");
        employeeRepository.save(employee);
    }

    @Test
    void testFindByUsername() {
        // Testowanie wyszukiwania po nazwie użytkownika
        Optional<Employee> foundEmployee = employeeRepository.findByUsername("john.doe");
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getUsername()).isEqualTo("john.doe");
    }

    @Test
    void testFindByEmail() {
        // Testowanie wyszukiwania po adresie email
        Optional<Employee> foundEmployee = employeeRepository.findByEmail("john.doe@example.com");
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindByEmployeeId() {
        // Testowanie wyszukiwania po employeeId
        Optional<Employee> foundEmployee = employeeRepository.findByEmployeeId(1L);
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getEmployeeId()).isEqualTo(1L);
    }

    @Test
    void testUpdateEnabledByEmployeeId() {
        // Testowanie aktualizacji pola 'enabled' w obiekcie Employee
        employeeRepository.updateEnabledByEmployeeId(1L, false);
        Optional<Employee> updatedEmployee = employeeRepository.findByEmployeeId(1L);
        assertThat(updatedEmployee).isPresent();
        assertThat(updatedEmployee.get().isEnabled()).isFalse();
    }

    @Test
    void testUpdateEmailCodeByEmployeeId() {
        // Testowanie aktualizacji pola 'emailCode'
        employeeRepository.updateEmailCodeByEmployeeId(1L, "123456");
        Optional<Employee> updatedEmployee = employeeRepository.findByEmployeeId(1L);
        assertThat(updatedEmployee).isPresent();
        assertThat(updatedEmployee.get().getEmailCode()).isEqualTo("123456");
    }

//    @Test
//    void testChangePasswordByEmployeeId() {
//        // Testowanie aktualizacji pola 'password'
//        employeeRepository.changePasswordByEmployeeId(1L, "newPassword123");
//        Optional<Employee> updatedEmployee = employeeRepository.findByEmployeeId(1L);
//        assertThat(updatedEmployee).isPresent();
//        assertThat(updatedEmployee.get().getPassword()).isEqualTo("newPassword123");
//    }

    @Test
    void testChangePasswordByEmployeeId() {
        // Przygotowanie - zapisanie pracownika
        Employee employee = new Employee();
        employee.setUsername("john.doe");
        employee.setEmail("john.doe@example.com");
        employee.setPassword("old_password");
        employee = employeeRepository.saveAndFlush(employee);

        // Akcja - aktualizacja hasła
        employeeRepository.changePasswordByEmployeeId(employee.getEmployeeId(), "new_password");

        // Walidacja - sprawdzenie, czy hasło zostało zmienione
        Optional<Employee> updatedEmployee = employeeRepository.findByEmployeeId(employee.getEmployeeId());
        assertThat(updatedEmployee).isPresent();
        assertThat(updatedEmployee.get().getPassword()).isEqualTo("new_password");
    }

    @Test
    void testIsEnabledById() {
        // Testowanie metody isEnabledById
        boolean isEnabled = employeeRepository.isEnabledById(1L);
        assertThat(isEnabled).isTrue();

        // Zaktualizuj i sprawdź ponownie
        employeeRepository.updateEnabledByEmployeeId(1L, false);
        isEnabled = employeeRepository.isEnabledById(1L);
        assertThat(isEnabled).isFalse();
    }

}