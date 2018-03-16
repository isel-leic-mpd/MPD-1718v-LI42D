package pt.isel.mpd.v1718.li42d.weather.dataAccess.repositories;

import pt.isel.mpd.v1718.li42d.weather.dataAccess.WeatherInfoRepositoryBase;

import java.io.InputStream;
import java.time.LocalDate;

public class WorldWeatherOnlineCsvFileWeatherInfoRepository extends WeatherInfoRepositoryBase {
    private String path;


    public WorldWeatherOnlineCsvFileWeatherInfoRepository(String path) {
        this.path = path;
    }


    @Override
    protected InputStream getStream(String location, LocalDate start, LocalDate end) {
        return ClassLoader.getSystemResourceAsStream(path);
    }
}
