package pl.strefainformacji.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table (name = "employees")
@Getter
@Setter
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Size(min = 3)
    private String firstName;

    @Size(min = 3)
    private String lastName;

    @Size(min = 3)
    @Column(unique = true)
    private String email;

    @Size(min = 3)
    @Column(unique = true)
    private String username;

    @Size(min = 5)
    private String password;

    private boolean enabled;

    private String emailCode;
}