package pl.strefainformacji.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherDtoTest {

    @Test
    void testWeatherDtoBuilder() {
        float expectedTemperature = 23.5f;
        int expectedPressure = 1012;
        int expectedHumidity = 60;
        float expectedSpeed = 5.4f;

        WeatherDto weatherDto = WeatherDto.builder()
                .temperatue(expectedTemperature)
                .pressure(expectedPressure)
                .humidity(expectedHumidity)
                .speed(expectedSpeed)
                .build();

        assertThat(weatherDto.getTemperatue()).isEqualTo(expectedTemperature);
        assertThat(weatherDto.getPressure()).isEqualTo(expectedPressure);
        assertThat(weatherDto.getHumidity()).isEqualTo(expectedHumidity);
        assertThat(weatherDto.getSpeed()).isEqualTo(expectedSpeed);
    }

    @Test
    void testWeatherDtoWithNegativeValues() {
        float expectedTemperature = -5.0f;
        int expectedPressure = 950;
        int expectedHumidity = 30;
        float expectedSpeed = -2.5f;

        WeatherDto weatherDto = WeatherDto.builder()
                .temperatue(expectedTemperature)
                .pressure(expectedPressure)
                .humidity(expectedHumidity)
                .speed(expectedSpeed)
                .build();

        assertThat(weatherDto.getTemperatue()).isEqualTo(expectedTemperature);
        assertThat(weatherDto.getPressure()).isEqualTo(expectedPressure);
        assertThat(weatherDto.getHumidity()).isEqualTo(expectedHumidity);
        assertThat(weatherDto.getSpeed()).isEqualTo(expectedSpeed);
    }

    @Test
    void testWeatherDtoWithZeroValues() {
        float expectedTemperature = 0.0f;
        int expectedPressure = 0;
        int expectedHumidity = 0;
        float expectedSpeed = 0.0f;

        WeatherDto weatherDto = WeatherDto.builder()
                .temperatue(expectedTemperature)
                .pressure(expectedPressure)
                .humidity(expectedHumidity)
                .speed(expectedSpeed)
                .build();

        assertThat(weatherDto.getTemperatue()).isEqualTo(expectedTemperature);
        assertThat(weatherDto.getPressure()).isEqualTo(expectedPressure);
        assertThat(weatherDto.getHumidity()).isEqualTo(expectedHumidity);
        assertThat(weatherDto.getSpeed()).isEqualTo(expectedSpeed);
    }
}