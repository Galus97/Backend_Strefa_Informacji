package pl.strefainformacji.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.strefainformacji.component.CurrentEmployee;

@Controller
public class PanelController {

    @GetMapping("/panel")
    public String panel(@AuthenticationPrincipal CurrentEmployee curentEmployee, Model model){
        model.addAttribute("employee", curentEmployee.getEmployee());
        return "panel";
    }
}
