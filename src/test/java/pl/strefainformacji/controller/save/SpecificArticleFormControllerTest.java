package pl.strefainformacji.controller.save;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.EmployeeService;

import java.util.Collections;

@WebMvcTest(SpecificArticleFormController.class)
class SpecificArticleFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private CurrentEmployee currentEmployee;
    private TestingAuthenticationToken authToken;

    @BeforeEach
    void setup() {
        Employee emp = new Employee();
        emp.setEmployeeId(100L);
        currentEmployee = new CurrentEmployee(
                "user",
                "pass",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                emp
        );
        authToken = new TestingAuthenticationToken(
                currentEmployee,
                currentEmployee.getPassword(),
                currentEmployee.getAuthorities()
        );
        authToken.setAuthenticated(true);
    }

    
}
