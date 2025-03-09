package pl.strefainformacji.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.strefainformacji.entity.Employee;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByUsername() {
        // given
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .username("janedoe")
                .password("secretPassword")
                .enabled(true)
                .emailCode("code")
                .build();
        employeeRepository.save(employee);

        // when
        Optional<Employee> found = employeeRepository.findByUsername("janedoe");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("janedoe");
    }

    @Test
    public void testFindByEmail() {
        // given
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .username("janedoe")
                .password("secretPassword")
                .enabled(true)
                .emailCode("code")
                .build();
        employeeRepository.save(employee);

        // when
        Optional<Employee> found = employeeRepository.findByEmail("jane.doe@example.com");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("jane.doe@example.com");
    }

    @Test
    public void testUpdateEnabledByEmployeeId() {
        // given
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .username("janedoe")
                .password("secretPassword")
                .enabled(false)
                .emailCode("code")
                .build();
        employee = employeeRepository.save(employee);

        // when
        employeeRepository.updateEnabledByEmployeeId(employee.getEmployeeId(), true);
        employee = employeeRepository.findById(employee.getEmployeeId()).get();

        // then
        assertThat(employee.isEnabled()).isTrue();
    }

    @Test
    public void testUpdateEmailCodeByEmployeeId() {
        // given
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .username("janedoe")
                .password("secretPassword")
                .enabled(true)
                .emailCode("oldCode")
                .build();
        employee = employeeRepository.save(employee);

        // when
        employeeRepository.updateEmailCodeByEmployeeId(employee.getEmployeeId(), "newCode");
        employee = employeeRepository.findById(employee.getEmployeeId()).get();

        // then
        assertThat(employee.getEmailCode()).isEqualTo("newCode");
    }

    @Test
    public void testChangePasswordByEmployeeId() {
        // given
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .username("janedoe")
                .password("oldPassword")
                .enabled(true)
                .emailCode("code")
                .build();
        employee = employeeRepository.save(employee);

        // when
        employeeRepository.changePasswordByEmployeeId(employee.getEmployeeId(), "newPassword");
        employee = employeeRepository.findById(employee.getEmployeeId()).get();

        // then
        assertThat(employee.getPassword()).isEqualTo("newPassword");
    }

    @Test
    public void testChangeEmailByEmployeeId() {
        // given
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .username("janedoe")
                .password("password")
                .enabled(true)
                .emailCode("code")
                .build();
        employee = employeeRepository.save(employee);

        // when
        employeeRepository.changeEmailByEmployeeId(employee.getEmployeeId(), "new.email@example.com");
        employee = employeeRepository.findById(employee.getEmployeeId()).get();

        // then
        assertThat(employee.getEmail()).isEqualTo("new.email@example.com");
    }
}
