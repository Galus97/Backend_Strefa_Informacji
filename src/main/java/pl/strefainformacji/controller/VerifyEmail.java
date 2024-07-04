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
import pl.strefainformacji.service.EmployeeService;

@Controller
@AllArgsConstructor
public class VerifyEmail {

    private final EmployeeService employeeService;

    @GetMapping("/verifyEmail")
    public String verifyEmail(){
            return "verifyEmail";
    }

    @PostMapping("/verifyEmail")
    public String verifyEmailPost(HttpServletRequest request, @AuthenticationPrincipal CurrentEmployee curentEmployee){
        String verifyEmailCode = request.getParameter("verifyEmailCode");
        if(verifyEmailCode.equals(curentEmployee.getEmployee().getEmailCode())){
            employeeService.updateEnable(curentEmployee.getEmployee().getEmployeeId(), true);
            System.out.println();
            return "redirect:panel";
        } else {
            return "verifyEmail";
        }
    }
}
