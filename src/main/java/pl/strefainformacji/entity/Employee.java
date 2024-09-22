package pl.strefainformacji.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Size(min = 3, message = "{Size.employee.firstName}")
    private String firstName;

    @Size(min = 3, message = "{Size.employee.lastName}")
    private String lastName;

    @Size(min = 5, message = "{Size.employee.email}")
    @Column(unique = true)
    @Email(message = "{Email.employee.email}")
    private String email;

    @Size(min = 3, message = "{Size.employee.username}")
    @Column(unique = true)
    private String username;

    @Size(min = 5, message = "{Size.employee.password}")
    private String password;

    private boolean enabled;

    private String emailCode;
}