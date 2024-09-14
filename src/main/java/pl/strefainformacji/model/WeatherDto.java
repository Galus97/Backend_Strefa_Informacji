package pl.strefainformacji.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherDto {

    private float temperatue;
    private int pressure;
    private int humidity;
    private float speed;
}