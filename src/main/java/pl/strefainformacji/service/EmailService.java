package pl.strefainformacji.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final HttpServletRequest request;

    @Async
    public String sendEmail(){
        HttpSession registerEmail = request.getSession();
        String email = (String) registerEmail.getAttribute("registerEmail");

        if(email != null){
            String emailActiveCode = generateActiveCode();
            SimpleMailMessage message = new SimpleMailMessage();
            String text = "Twój kod aktywacyjny to: " + emailActiveCode;

            message.setTo(email);
            message.setFrom("projektkoncowymichal@gmail.com");
            message.setSubject("Kod aktywacyjny Strefa Informacji");
            message.setText(text);

            javaMailSender.send(message);
            registerEmail.removeAttribute("registerEmail");
            return emailActiveCode;
        } else {
            System.out.println("Błąd: Adres e-mail rejestracji nie został ustawiony w sesji.");
            return null;
        }

    }
    private String generateActiveCode(){
        Random random = new Random();
        return String.valueOf(random.nextInt(1000, 9999));
    }
}