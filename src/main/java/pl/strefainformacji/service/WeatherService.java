package pl.strefainformacji.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.strefainformacji.model.WeatherDto;
import pl.strefainformacji.webclient.weather.WeatherClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private static final double LAT = 52.23;
    private static final double LON = 21.01;

    private final WeatherClient weatherClient;

    public WeatherDto getWeather() {
        return weatherClient.getWeatherForCity(LAT, LON);
    }
}