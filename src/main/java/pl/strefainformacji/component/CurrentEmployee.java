package pl.strefainformacji.component;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.strefainformacji.entity.Employee;

/**
 * A custom Spring Security {@link User} implementation representing the currently authenticated employee.
 */
@Getter
public class CurrentEmployee extends User {
    private final Employee employee;

    /**
     * Constructs a new {@link CurrentEmployee} instance.
     *
     * @param username    The username of the employee.
     * @param password    The password of the employee.
     * @param authorities The authorities (roles) granted to the employee.
     * @param employee    The {@link Employee} entity associated with the authenticated user.
     */
    public CurrentEmployee(String username, String password, java.util.Collection<?
            extends GrantedAuthority> authorities, Employee employee) {
        super(username, password, authorities);
        this.employee = employee;
    }
}