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
    private static final String CHANGE_EMAIL_PAGE = "changeEmail";
    private final EmployeeService employeeService;

    @GetMapping("/changeEmail")
    public String showChangeEmailForm(@AuthenticationPrincipal CurrentEmployee currentEmployee) {
        if (employeeService.isEnabledById(currentEmployee.getEmployee().getEmployeeId())) {
            return CHANGE_EMAIL_PAGE;
        } else {
            return "redirect:verifyEmail";
        }
    }

    @PostMapping("/changeEmail")
    public String saveChangedEmail(@AuthenticationPrincipal CurrentEmployee currentEmployee, HttpServletRequest request) {
        String newEmail = request.getParameter("newEmail");
        String newEmailAgain = request.getParameter("newEmailAgain");

        if (!newEmail.equals(newEmailAgain)) {
            return CHANGE_EMAIL_PAGE;
        }

        if (newEmail.equals(currentEmployee.getEmployee().getEmail())) {
            return CHANGE_EMAIL_PAGE;
        }

        employeeService.changeEmail(currentEmployee.getEmployee().getEmployeeId(), newEmail);
        currentEmployee.getEmployee().setEmail(newEmail);
        return "redirect:panel";
    }
}