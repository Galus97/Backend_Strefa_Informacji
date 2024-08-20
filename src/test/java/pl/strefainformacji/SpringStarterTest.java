package pl.strefainformacji;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SpringStarterTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Test sprawdza, czy kontekst aplikacji ładuje się poprawnie
        assertNotNull(applicationContext, "Application context should not be null");
    }

    @Test
    void mainMethodTest() {
        // Test sprawdza, czy metoda main uruchamia aplikację bez błędów
        SpringStarter.main(new String[]{});
        // Można tutaj dodać dodatkowe sprawdzenia, jeśli są potrzebne
    }

}