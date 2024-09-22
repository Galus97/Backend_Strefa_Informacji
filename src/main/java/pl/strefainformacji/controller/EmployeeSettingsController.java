package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.service.EmployeeService;

@Controller
@RequiredArgsConstructor
public class EmployeeSettingsController {
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/settings")
    public String settingsGet(@AuthenticationPrincipal CurrentEmployee curentEmployee) {
        if (employeeService.isEnabledById(curentEmployee.getEmployee().getEmployeeId())) {
            return "settings";
        } else {
            return "redirect:verifyEmail";
        }

    }

    @PostMapping("/settings")
    public String settingsPost(@AuthenticationPrincipal CurrentEmployee curentEmployee, HttpServletRequest request, Model model) {
        String lastPassword = request.getParameter("lastPassword");
        String newPassword = request.getParameter("newPassword");
        String newPasswordAgain = request.getParameter("newPasswordAgain");

        String encodedPassword = curentEmployee.getEmployee().getPassword();

        if (!passwordEncoder.matches(lastPassword, encodedPassword)) {
            model.addAttribute("wrongPassword", "Podane przez Ciebie hasło nie jest identyczne niż używane do tej pory");
            return "settings";
        }

        if (!newPassword.equals(newPasswordAgain)) {
            model.addAttribute("passwordsDoNotMatch", "Podane przez Ciebie nowe hasło nie jest zgodne z jego powtórzeniem");
            return "settings";
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        employeeService.changePassword(curentEmployee.getEmployee().getEmployeeId(), encodedNewPassword);
        curentEmployee.getEmployee().setPassword(encodedNewPassword);

        return "redirect:panel";
    }
}
