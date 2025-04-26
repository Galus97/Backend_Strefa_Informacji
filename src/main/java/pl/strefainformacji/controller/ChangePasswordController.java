package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.EmployeeService;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class ChangePasswordController {
    private static final String CHANGE_PASSWORD_PAGE = "changePassword";
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @GetMapping("/changePassword")
    public String showChangePasswordForm(@AuthenticationPrincipal CurrentEmployee currentEmployee) {
        if (employeeService.isEnabledById(currentEmployee.getEmployee().getEmployeeId())) {
            return CHANGE_PASSWORD_PAGE;
        } else {
            return "redirect:verifyEmail";
        }

    }

    @PostMapping("/changePassword")
    public String saveChangedPassword(@AuthenticationPrincipal CurrentEmployee currentEmployee, HttpServletRequest request, Model model) {
        String lastPassword = request.getParameter("lastPassword");
        String newPassword = request.getParameter("newPassword");
        String newPasswordAgain = request.getParameter("newPasswordAgain");

        String encodedPassword = currentEmployee.getEmployee().getPassword();

        String errorMessage;
        if (!passwordEncoder.matches(lastPassword, encodedPassword)) {
            errorMessage = messageSource.getMessage("error.wrongPassword", null, Locale.getDefault());
            model.addAttribute("wrongPassword", errorMessage);
            return CHANGE_PASSWORD_PAGE;
        }

        if (!newPassword.equals(newPasswordAgain)) {
            errorMessage = messageSource.getMessage("error.passwordsDoNotMatch", null, Locale.getDefault());
            model.addAttribute("passwordsDoNotMatch", errorMessage);
            return CHANGE_PASSWORD_PAGE;
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        employeeService.changePassword(currentEmployee.getEmployee().getEmployeeId(), encodedNewPassword);
        currentEmployee.getEmployee().setPassword(encodedNewPassword);

        return "redirect:panel";
    }
}
