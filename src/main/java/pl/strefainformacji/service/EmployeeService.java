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

    public void updateEnable(Long id, boolean value){
        employeeRepository.updateEnabledByEmployeeId(id, value);
    }

    public boolean isEnabledById(Long employeeId){
        return employeeRepository.isEnabledById(employeeId);
    }

    public Optional<Employee> findByEmployeeId(Long id){
        return employeeRepository.findByEmployeeId(id);
    }

    public void updateEmailCode(Long id, String emailCode){
        employeeRepository.updateEmailCodeByEmployeeId(id, emailCode);
    }

    public void changePassword(Long id, String password){
        employeeRepository.changePasswordByEmployeeId(id, password);
    }
}
