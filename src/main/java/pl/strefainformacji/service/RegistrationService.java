package pl.strefainformacji.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ValidationException;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    private Map<String, String> validate(Employee employee) {
        Optional<Employee> employeeExistByUsername = employeeRepository.findByUsername(employee.getUsername());
        Optional<Employee> employeeExistByEmail = employeeRepository.findByEmail(employee.getEmail());
        Map<String, String> errors = new HashMap<>();
        if (employeeExistByUsername.isPresent()) {
            errors.put("existUsername", "Użytkownik z taką nazwą już istnieje. Wpisz inną nazwę użytkownika");
        }
        if (employeeExistByEmail.isPresent()) {
            errors.put("existEmail", "Ten adres email jest już używany. Wpisz inny adres email");
        }
        return errors;
    }

    public Employee newEmployeeRegistration(Employee employee) throws pl.strefainformacji.exception.ValidationException {
        Map<String, String> validationFailures = validate(employee);
        if (validationFailures.isEmpty()) {
            employee.setEmployeeId(null);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            return employeeRepository.save(employee);
        } else {
            throw new ValidationException(validationFailures);
        }
    }
}