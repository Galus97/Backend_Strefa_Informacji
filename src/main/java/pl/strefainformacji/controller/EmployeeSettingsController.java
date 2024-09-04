package pl.strefainformacji.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeSettingsController {

    @GetMapping("/settings")
    public String settingsGet(){
        return "settings";
    }
}
