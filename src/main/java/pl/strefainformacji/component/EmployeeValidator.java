package pl.strefainformacji.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeValidator {

    private final EmployeeRepository employeeRepository;

    public Map<String, String> validate(Employee employee) {
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
}
