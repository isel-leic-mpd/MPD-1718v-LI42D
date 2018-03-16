package pt.isel.mpd.v1718.li42d.weather.dataAccess.repositories;

import pt.isel.mpd.v1718.li42d.weather.dataAccess.DailyWeatherInfoDto;
import pt.isel.mpd.v1718.li42d.weather.dataAccess.WeatherInfoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class InMemoryWeatherInfoRepository implements WeatherInfoRepository {
    private Collection<DailyWeatherInfoDto> data;


    public InMemoryWeatherInfoRepository() {
        this.data = new ArrayList<>();

        data.add(new DailyWeatherInfoDto(15, 12, LocalDate.of(2018,3,1)));
        data.add(new DailyWeatherInfoDto(12, 10, LocalDate.of(2018,3,3)));
    }

    @Override
    public Collection<DailyWeatherInfoDto> getDailyWeatherInfoDtos(String location, LocalDate start, LocalDate end) {
        return data;
    }
}
