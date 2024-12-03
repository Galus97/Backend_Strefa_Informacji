package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

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

    public Optional<Employee> findByEmployeeId(Long id) {
        if (employeeRepository.findByEmployeeId(id).isPresent()) {
            return employeeRepository.findByEmployeeId(id);
        } else {
            return Optional.empty();
        }
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