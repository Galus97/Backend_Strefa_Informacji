package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.strefainformacji.repository.EmployeeRepository;

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
}
