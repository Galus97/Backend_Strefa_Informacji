package pl.strefainformacji.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomEmployeeDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with username: " + username));

        return new CurrentEmployee(
                employee.getUsername(),
                employee.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), employee);
    }
}
