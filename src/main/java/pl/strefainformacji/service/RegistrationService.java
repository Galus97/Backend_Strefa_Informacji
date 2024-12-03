package pl.strefainformacji.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.EmployeeValidator;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ValidationException;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeValidator employeeValidator;

    public Employee newEmployeeRegistration(Employee employee) throws pl.strefainformacji.exception.ValidationException {
        Map<String, String> validationFailures = employeeValidator.validate(employee);
        if (validationFailures.isEmpty()) {
            employee.setEmployeeId(null);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            return employeeRepository.save(employee);
        } else {
            throw new ValidationException(validationFailures);
        }
    }
}