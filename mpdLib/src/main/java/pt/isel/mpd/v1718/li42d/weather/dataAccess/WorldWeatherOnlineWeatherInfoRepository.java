package pt.isel.mpd.v1718.li42d.weather.dataAccess;

import java.time.LocalDate;
import java.util.Collection;

public class WorldWeatherOnlineWeatherInfoRepository implements WeatherInfoRepository {

    @Override
    public Collection<DailyWeatherInfoDto> getDailyWeatherInfoDtos(String location, LocalDate start, LocalDate end) {
        return null;
    }
}
