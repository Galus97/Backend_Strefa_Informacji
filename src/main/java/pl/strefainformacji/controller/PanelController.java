package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.EmployeeService;

@Controller
@AllArgsConstructor
public class PanelController {

    private final EmployeeService employeeService;

    @GetMapping("/panel")
    public String panel(@AuthenticationPrincipal CurrentEmployee curentEmployee, Model model) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            model.addAttribute("employee", curentEmployee.getEmployee());
            return "panel";
        } else {
            return "redirect:verifyEmail";
        }
    }
}