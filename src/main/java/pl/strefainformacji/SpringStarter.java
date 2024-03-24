package pl.strefainformacji;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import pl.strefainformacji.service.EmailService;

import java.util.Random;

@SpringBootApplication
@AllArgsConstructor
public class SpringStarter {

    private EmailService emailService;
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringStarter.class, args);
        SpringStarter app = context.getBean(SpringStarter.class);

        app.Run();

    }

    private void Run(){
        Random random = new Random();
        random.nextInt(1000, 9999);
        emailService.sendEmail("mgalus7@gmail.com", "Test mail subject", "Test mail body");
    }
}
