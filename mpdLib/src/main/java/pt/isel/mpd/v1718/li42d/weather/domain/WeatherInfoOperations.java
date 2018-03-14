package pt.isel.mpd.v1718.li42d.weather.domain;

import java.time.LocalDate;
import java.util.Collection;

public interface WeatherInfoOperations {
    Collection<DailyWeatherInfo> getDailyWeatherInfo(String location, LocalDate start, LocalDate end);
}
