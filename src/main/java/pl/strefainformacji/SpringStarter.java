package pl.strefainformacji;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import pl.strefainformacji.service.EmailService;

@SpringBootApplication
public class SpringStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringStarter.class, args);
    }
}