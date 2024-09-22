package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class LoginEmployeeController {
    @GetMapping("/login")
    public String loginGet() {
        return "login";
    }
}