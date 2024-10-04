package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/changeEmail")
    public String changeEmailPost(@AuthenticationPrincipal CurrentEmployee curentEmployee, HttpServletRequest request) {
        String newEmail = request.getParameter("newEmail");
        String newEmailAgain = request.getParameter("newEmailAgain");

        if (!newEmail.equals(newEmailAgain)) {
            return "changeEmail";
        }

        if (newEmail.equals(curentEmployee.getEmployee().getEmail())) {
            return "changeEmail";
        }

        employeeService.changeEmail(curentEmployee.getEmployee().getEmployeeId(), newEmail);
        curentEmployee.getEmployee().setEmail(newEmail);
        return "redirect:panel";
    }
}
