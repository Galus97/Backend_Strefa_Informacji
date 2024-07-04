package pl.strefainformacji.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class LoginEmployeeController {
    @GetMapping("/login")
    public String loginGet(){
        return "login";
    }
}
