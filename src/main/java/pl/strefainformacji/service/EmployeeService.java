package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.EmployeeNotFoundException;
import pl.strefainformacji.repository.EmployeeRepository;

@Service
@AllArgsConstructor
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
        employeeRepository.deleteById(employeeId);
    }


    public void updateEnable(Long employeeId, boolean value) {
        isEmployeeExistOrThrow(employeeId);

        employeeRepository.updateEnabledByEmployeeId(employeeId, value);

    }

    public boolean isEnabledById(Long employeeId) {
        isEmployeeExistOrThrow(employeeId);

        return employeeRepository.isEnabledById(employeeId);
    }


    public void updateEmailCode(Long employeeId, String emailCode) {
        if (validateData(employeeId, emailCode)) {
            employeeRepository.updateEmailCodeByEmployeeId(employeeId, emailCode);
        }
    }

    public void changePassword(Long employeeId, String password) {
        if (validateData(employeeId, password)) {
            employeeRepository.changePasswordByEmployeeId(employeeId, password);
        }
    }

    public void changeEmail(Long employeeId, String email) {
        if (validateData(employeeId, email)) {
            employeeRepository.changeEmailByEmployeeId(employeeId, email);
        }
    }

    public boolean isEmployeeExistOrThrow(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException(messageService.getMessage("error.employeeNotFound", employeeId));
        }
        return true;
    }

    private boolean validateData(Long id, String text) {
        return employeeRepository.findByEmployeeId(id).isPresent() && text != null && !text.isBlank();
    }
}