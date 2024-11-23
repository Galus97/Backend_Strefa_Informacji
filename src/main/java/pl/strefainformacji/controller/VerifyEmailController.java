package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.service.EmailService;
import pl.strefainformacji.service.EmployeeService;

@Controller
@AllArgsConstructor
public class VerifyEmailController {

    private final EmployeeService employeeService;
    private final EmailService emailService;

    @GetMapping("/verifyEmail")
    public String verifyEmail() {
        return "verifyEmail";
    }

    @PostMapping("/verifyEmail")
    public String verifyEmailPost(HttpServletRequest request, @AuthenticationPrincipal CurrentEmployee currentEmployee) {
        Employee employee = currentEmployee.getEmployee();
        String action = request.getParameter("action");
        if ("Wyślij".equals(action)) {
            String verifyEmailCode = request.getParameter("verifyEmailCode");
            if (verifyEmailCode.equals(currentEmployee.getEmployee().getEmailCode())) {
                employeeService.updateEnable(currentEmployee.getEmployee().getEmployeeId(), true);
                return "redirect:panel";
            } else {
                return "verifyEmail";
            }
        } else if ("ResendCode".equals(action)) {
            String emailCode = emailService.valueOfEmailActiveCode();
            employee.setEmailCode(emailCode);
            sendActivationEmail(request, currentEmployee.getEmployee().getEmail());
            employeeService.updateEmailCode(currentEmployee.getEmployee().getEmployeeId(), emailCode);
        }
        return "verifyEmail";
    }

    private void sendActivationEmail(HttpServletRequest request, String email) {
        HttpSession registerEmail = request.getSession();
        registerEmail.setAttribute("registerEmail", email);
        emailService.sendEmail();
    }
}