package pl.strefainformacji.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.EmployeeService;

@Controller
@RequiredArgsConstructor
public class EmployeeSettingsController {
    private final EmployeeService employeeService;

    @GetMapping("/settings")
    public String settingsGet(@AuthenticationPrincipal CurrentEmployee curentEmployee) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            return "settings";
        } else {
            return "redirect:verifyEmail";
        }
    }
}