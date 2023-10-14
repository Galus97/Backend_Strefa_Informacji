package pl.strefainformacji.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/articles")
                .allowedOrigins("http://localhost:3000", "http://2.57.137.47", "http://dev.strefainformacji.pl");
    }
}
