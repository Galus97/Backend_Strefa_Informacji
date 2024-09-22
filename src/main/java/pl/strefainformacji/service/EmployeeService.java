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
            throw new NullPointerException();
        }
    }

    public void updateEmailCode(Long id, String emailCode) {
        if (employeeRepository.findByEmployeeId(id).isPresent() && emailCode != null && !emailCode.isBlank()) {
            employeeRepository.updateEmailCodeByEmployeeId(id, emailCode);
        }
    }

    public void changePassword(Long id, String password) {
        if (employeeRepository.findByEmployeeId(id).isPresent() && password != null && !password.isBlank()) {
            employeeRepository.changePasswordByEmployeeId(id, password);
        }
    }
}