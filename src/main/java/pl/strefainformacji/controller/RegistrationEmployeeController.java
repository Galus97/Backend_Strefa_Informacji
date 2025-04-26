package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ValidationException;
import pl.strefainformacji.service.EmailService;
import pl.strefainformacji.service.RegistrationService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationEmployeeController {
    private static final String REGISTER_PAGE = "register";

    private final RegistrationService registrationService;
    private final EmailService emailService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("employee", new Employee());
        return REGISTER_PAGE;
    }

    @PostMapping("/register")
    public String saveNewEmployee(@Valid Employee employee, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return REGISTER_PAGE;
        }
        try {
            employee.setEmailCode(emailService.getVerificationCode(employee.getEmail()));
            registrationService.newEmployeeRegistration(employee);
            sendActivationEmail(request, employee.getEmail());
            return "redirect:login";
        } catch (ValidationException exception) {
            Map<String, String> errors = exception.getValidationErrors();
            if (errors.containsKey("existUsername")) {
                bindingResult.rejectValue("username", "", errors.get("existUsername"));
            }
            if (errors.containsKey("existEmail")) {
                bindingResult.rejectValue("email", "", errors.get("existEmail"));
            }

            return REGISTER_PAGE;
        }
    }

    private void sendActivationEmail(HttpServletRequest request, String email) {
        HttpSession registerEmail = request.getSession();
        registerEmail.setAttribute("registerEmail", email);
        emailService.sendEmail(email);
    }
}