package pl.strefainformacji.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.strefainformacji.entity.Employee;
import pl.strefainformacji.exception.ValidationException;
import pl.strefainformacji.service.EmailService;
import pl.strefainformacji.service.RegistrationService;

import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeeRegistrationController {

    private final RegistrationService registrationService;
    private final EmailService emailService;

    @GetMapping("/register")
    public String registerGet(Model model){
        model.addAttribute("employee", new Employee());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid Employee employee, BindingResult bindingResult, HttpServletRequest request, Model model){
        if(bindingResult.hasErrors()){
            return "register";
        }
        try{
            String emailCode = sendActivationEmail(request, employee.getEmail());
            employee.setEmailCode(emailCode);
            registrationService.newEmployeeRegistration(employee);
            return "redirect:login";
        } catch (ValidationException exception){
            List<String> errors = exception.getValidationErrors();
            model.addAttribute("errors", errors);
            return "register";
        }
    }

    private String sendActivationEmail(HttpServletRequest request, String email){
        HttpSession registerEmail = request.getSession();
        registerEmail.setAttribute("registerEmail", email);
        return emailService.sendEmail();
    }
}