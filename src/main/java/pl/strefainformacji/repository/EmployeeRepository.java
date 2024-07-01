package pl.strefainformacji.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.strefainformacji.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.enabled = :enabled WHERE e.id = :employeeId")
    void updateEnabledByEmployeeId(@Param("employeeId") Long employeeId, @Param("enabled") boolean enabled);

    default boolean isEnabledById(Long employeeId) {
        return existsById(employeeId) && getOne(employeeId).isEnabled();
    }
}
