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

    public void updateEnable(Long id, boolean value) {
        if (findByEmployeeId(id).isPresent()) {
            employeeRepository.updateEnabledByEmployeeId(id, value);
        }
    }

    public boolean isEnabledById(Long employeeId) {
        if (findByEmployeeId(employeeId).isPresent()) {
            return employeeRepository.isEnabledById(employeeId);
        }
        throw new NullPointerException("No such employee found");
    }

    public Employee findByEmployeeId(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(messageService.getMessage("error.employeeNotFound", employeeId)));
    }

    public void updateEmailCode(Long id, String emailCode) {
        if (validateData(id, emailCode)) {
            employeeRepository.updateEmailCodeByEmployeeId(id, emailCode);
        }
    }

    public void changePassword(Long id, String password) {
        if (validateData(id, password)) {
            employeeRepository.changePasswordByEmployeeId(id, password);
        }
    }

    public void changeEmail(Long id, String email) {
        if (validateData(id, email)) {
            employeeRepository.changeEmailByEmployeeId(id, email);
        }
    }

    private boolean validateData(Long id, String text) {
        return employeeRepository.findByEmployeeId(id).isPresent() && text != null && !text.isBlank();
    }
}