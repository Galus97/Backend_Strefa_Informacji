package pl.strefainformacji.webclient.weather;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.strefainformacji.model.WeatherDto;
import pl.strefainformacji.webclient.weather.dto.OpenWeatherDto;

@Component
public class WeatherClient {
    private static final String WEATER_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "104430623b731b9a297d4421d09703ce";
    private RestTemplate restTemplate = new RestTemplate();

    public WeatherDto getWeatherForCity(double lat, double lon) {
        OpenWeatherDto openWeatherDto = restTemplate.getForObject(
                WEATER_URL + "weather?lat={lat}&lon={lon}&appid={apiKey}&units=metric&lang=pl",
                OpenWeatherDto.class, lat, lon, API_KEY);
        return WeatherDto.builder()
                .temperatue(openWeatherDto.getMain().getTemp())
                .pressure(openWeatherDto.getMain().getPressure())
                .humidity(openWeatherDto.getMain().getHumidity())
                .speed(openWeatherDto.getWind().getSpeed())
                .build();
    }
}
