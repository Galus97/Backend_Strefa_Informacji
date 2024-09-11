package pl.strefainformacji.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.strefainformacji.model.WeatherDto;
import pl.strefainformacji.webclient.weather.WeatherClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private WeatherClient weatherClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeather() {
        WeatherDto weatherDto = WeatherDto.builder()
                .temperatue(22f)
                .pressure(1015)
                .humidity(65)
                .speed(4.5f)
                .build();

        when(weatherClient.getWeatherForCity(52.23, 21.01)).thenReturn(weatherDto);

        WeatherDto result = weatherService.getWeather();

        assertNotNull(result);
        assertEquals(22.0, result.getTemperatue());
        assertEquals(1015, result.getPressure());
        assertEquals(65, result.getHumidity());
        assertEquals(4.5, result.getSpeed());
    }
}