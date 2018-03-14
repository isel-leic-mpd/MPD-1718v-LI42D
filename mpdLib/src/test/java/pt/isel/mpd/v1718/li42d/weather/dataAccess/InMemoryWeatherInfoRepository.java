package pt.isel.mpd.v1718.li42d.weather.dataAccess;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class InMemoryWeatherInfoRepository implements WeatherInfoRepository {
    private Collection<DailyWeatherInfoDto> data;


    public InMemoryWeatherInfoRepository() {
        this.data = new ArrayList<>();

        data.add(new DailyWeatherInfoDto(15, 12));
        data.add(new DailyWeatherInfoDto(12, 10));
    }

    @Override
    public Collection<DailyWeatherInfoDto> getDailyWeatherInfoDtos(String location, LocalDate start, LocalDate end) {
        return data;
    }
}
