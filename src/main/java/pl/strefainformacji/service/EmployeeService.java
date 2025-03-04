package pl.strefainformacji.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.EmployeeNotFoundException;
import pl.strefainformacji.repository.EmployeeRepository;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MessageService messageService;

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(messageService.getMessage("error.employeeNotFound", employeeId)));
    }

    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("error.employeeNotFound"));
        employeeRepository.delete(employee);
    }


    public void updateEnable(Long employeeId, boolean value) {
        validateEmployeeExists(employeeId);

        employeeRepository.updateEnabledByEmployeeId(employeeId, value);

    }

    public boolean isEnabledById(Long employeeId) {
        validateEmployeeExists(employeeId);

        return employeeRepository.isEnabledById(employeeId);
    }


    public void updateEmailCode(Long employeeId, String emailCode) {
        if (validateEmployeeAndText(employeeId, emailCode)) {
            employeeRepository.updateEmailCodeByEmployeeId(employeeId, emailCode);
        }
    }

    public void changePassword(Long employeeId, String password) {
        if (validateEmployeeAndText(employeeId, password)) {
            employeeRepository.changePasswordByEmployeeId(employeeId, password);
        }
    }

    public void changeEmail(Long employeeId, String email) {
        if (validateEmployeeAndText(employeeId, email)) {
            employeeRepository.changeEmailByEmployeeId(employeeId, email);
        }
    }

    public void validateEmployeeExists(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException(messageService.getMessage("error.employeeNotFound", employeeId));
        }
    }

    private boolean validateEmployeeAndText(Long employeeId, String text) {
        validateEmployeeExists(employeeId);
        return text != null && !text.isBlank();
    }
}