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

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByEmployeeId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.enabled = :enabled WHERE e.employeeId = :employeeId")
    void updateEnabledByEmployeeId(@Param("employeeId") Long employeeId, @Param("enabled") boolean enabled);

    default boolean isEnabledById(Long employeeId) {
        return existsById(employeeId) && getOne(employeeId).isEnabled();
    }

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.emailCode = :emailCode WHERE e.employeeId = :employeeId")
    void updateEmailCodeByEmployeeId(@Param("employeeId") Long employeeId, @Param("emailCode") String emailCode);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.password = :password WHERE e.employeeId = :employeeId")
    void changePasswordByEmployeeId(@Param("employeeId") Long employeeId, @Param("password") String password);
}
