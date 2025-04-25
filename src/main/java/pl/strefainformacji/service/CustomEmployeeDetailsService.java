package pl.strefainformacji.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.component.ErrorMessages;
import pl.strefainformacji.component.MessageService;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.repository.EmployeeRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomEmployeeDetailsService implements UserDetailsService {
    private static final String ROLE_USER = "ROLE_USER";
    private final EmployeeRepository employeeRepository;
    private final MessageService messageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        messageService.getMessage(ErrorMessages.EMPLOYEE_NOT_FOUND_BY_USERNAME, username)));

        return new CurrentEmployee(
                employee.getUsername(),
                employee.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)), employee);
    }
}