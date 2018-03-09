package pt.isel.mpd.v1718.li42d.weather.domain;

import pt.isel.mpd.v1718.li42d.weather.dataAccess.DailyWeatherInfoDto;
import pt.isel.mpd.v1718.li42d.weather.dataAccess.WeatherInfoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class WeatherInfoOperationsImpl implements WeatherInfoOperations {
    private WeatherInfoRepository weatherRepository;

    public WeatherInfoOperationsImpl(WeatherInfoRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public Collection<DailyWeatherInfo> getDailyWeatherInfo(LocalDate start, LocalDate end) {
        Collection<DailyWeatherInfoDto> dwiDtos = weatherRepository.getDailyWeatherInfoDtos();
        final ArrayList<DailyWeatherInfo> dwi = new ArrayList<>();
        dwi.add(new DailyWeatherInfo(11, 10));
        return dwi;
    }
}
