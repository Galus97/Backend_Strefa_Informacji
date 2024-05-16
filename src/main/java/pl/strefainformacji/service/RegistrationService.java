package pl.strefainformacji.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ValidationException;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class RegistrationService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    private List<String> validate(Employee employee) {
        Optional<Employee> employeeExist = employeeRepository.findByUsername(employee.getUsername());
        List<String> errors = new ArrayList<>();
        if (employeeExist.isPresent()) {
            errors.add("Użytkownik z taką nazwą użytkownika już istnieje. Musisz wpisać inny adres e-mail");
        }
        return errors;
    }

    public Employee newEmployeeRegistration(Employee employee) throws pl.strefainformacji.exception.ValidationException {
        List<String> validationFailures = validate(employee);
        if (validationFailures.isEmpty()) {
            employee.setEmployeeId(null);
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            return employeeRepository.save(employee);
        } else {
            throw new ValidationException(validationFailures);
        }
    }
}
