package pl.strefainformacji.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.EmployeeService;

@Controller
@RequiredArgsConstructor
public class ChangeEmailController {
    private final EmployeeService employeeService;

    @GetMapping("/changeEmail")
    public String changeEmailGet(@AuthenticationPrincipal CurrentEmployee curentEmployee) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            return "changeEmail";
        } else {
            return "redirect:verifyEmail";
        }
    }
}
