package pl.strefainformacji.component;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.strefainformacji.entity.Employee;

public class CurrentEmployee extends User {
    private final Employee employee;

    public CurrentEmployee(String username, String password, java.util.Collection<?
            extends GrantedAuthority> authorities, Employee employee){
        super(username, password, authorities);
        this.employee = employee;
    }
    public Employee getEmployee(){
        return employee;
    }
}
