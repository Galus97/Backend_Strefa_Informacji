package pl.strefainformacji.webclient.weather.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OpenWeatherWindDtoTest {

    @Test
    void testGetSpeed() {
        OpenWeatherWindDto dto = new OpenWeatherWindDto();

        setField(dto, "speed", 5.5f);

        float speed = dto.getSpeed();

        assertThat(speed).isEqualTo(5.5f);
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