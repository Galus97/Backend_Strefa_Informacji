package pl.strefainformacji.webclient.weather.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OpenWeatherDtoTest {

    private OpenWeatherDto openWeatherDto;
    private OpenWeatherMainDto mainDto;
    private OpenWeatherWindDto windDto;

    @BeforeEach
    public void setUp() {
        mainDto = mock(OpenWeatherMainDto.class);
        windDto = mock(OpenWeatherWindDto.class);

        openWeatherDto = new OpenWeatherDto();

        setField(openWeatherDto, "main", mainDto);
        setField(openWeatherDto, "wind", windDto);
    }

    @Test
    public void testGetMain() {
        assertThat(openWeatherDto.getMain()).isEqualTo(mainDto);
    }

    @Test
    public void testGetWind() {
        assertThat(openWeatherDto.getWind()).isEqualTo(windDto);
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}