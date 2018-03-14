package pt.isel.mpd.v1718.li42d.weather.dataAccess;

import java.time.LocalDate;
import java.util.Collection;

public interface WeatherInfoRepository {
    Collection<DailyWeatherInfoDto> getDailyWeatherInfoDtos(String location, LocalDate start, LocalDate end);
}
