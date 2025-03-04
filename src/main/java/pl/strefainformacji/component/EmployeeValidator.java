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
    private final MessageService messageService;

    public Map<String, String> validate(Employee employee) {
        Optional<Employee> employeeExistByUsername = employeeRepository.findByUsername(employee.getUsername());
        Optional<Employee> employeeExistByEmail = employeeRepository.findByEmail(employee.getEmail());
        Map<String, String> errors = new HashMap<>();
        if (employeeExistByUsername.isPresent()) {
            errors.put("existUsername", messageService.getMessage("error.employeeWithThisNameExist"));
        }
        if (employeeExistByEmail.isPresent()) {
            errors.put("existEmail", messageService.getMessage("error.employeeWithThisEmailExist"));
        }
        return errors;
    }
}
