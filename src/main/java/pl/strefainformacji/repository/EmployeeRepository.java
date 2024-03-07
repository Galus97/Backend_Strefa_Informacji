package pl.strefainformacji.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.strefainformacji.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByEmail(String username);
}
