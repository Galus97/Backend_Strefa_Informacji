package pl.strefainformacji.repository;

import pl.strefainformacji.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findByUsername(String username);
}
