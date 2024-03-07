package pl.strefainformacji.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.strefainformacji.component.CurrentEmployee;
import pl.strefainformacji.entity.Employee;

@Controller
public class PanelController {

    @GetMapping("/panel")
    public String panel(@AuthenticationPrincipal CurrentEmployee curentEmployee, Model model){
        model.addAttribute("employee", curentEmployee.getEmployee());
        return "panel";
    }
}
