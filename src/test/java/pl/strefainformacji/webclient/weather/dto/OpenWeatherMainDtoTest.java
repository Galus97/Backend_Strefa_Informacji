package pl.strefainformacji.webclient.weather.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OpenWeatherMainDtoTest {

    @Test
    void testGetTemp() {
        OpenWeatherMainDto dto = new OpenWeatherMainDto();

        setField(dto, "temp", 22.5f);

        float temp = dto.getTemp();

        assertThat(temp).isEqualTo(22.5f);
    }

    @Test
    void testGetPressure() {
        OpenWeatherMainDto dto = new OpenWeatherMainDto();

        setField(dto, "pressure", 1013);

        int pressure = dto.getPressure();

        assertThat(pressure).isEqualTo(1013);
    }

    @Test
    void testGetHumidity() {
        OpenWeatherMainDto dto = new OpenWeatherMainDto();

        setField(dto, "humidity", 65);

        int humidity = dto.getHumidity();

        assertThat(humidity).isEqualTo(65);
    }

    private void setField(Object targetObject, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = targetObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(targetObject, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}