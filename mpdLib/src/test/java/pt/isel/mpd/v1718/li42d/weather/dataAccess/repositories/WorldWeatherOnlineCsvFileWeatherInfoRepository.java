package pt.isel.mpd.v1718.li42d.weather.dataAccess.repositories;

import pt.isel.mpd.v1718.li42d.weather.dataAccess.DailyWeatherInfoDto;
import pt.isel.mpd.v1718.li42d.weather.dataAccess.WeatherInfoRepository;
import pt.isel.mpd.v1718.li42d.weather.dataAccess.WeatherInfoRepositoryBase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.rmi.server.ExportException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class WorldWeatherOnlineCsvFileWeatherInfoRepository extends WeatherInfoRepositoryBase {
    private String path;


    public WorldWeatherOnlineCsvFileWeatherInfoRepository(String path) {
        this.path = path;
    }

    @Override
    public Collection<DailyWeatherInfoDto> getDailyWeatherInfoDtos(String location, LocalDate start, LocalDate end) {
        return super.createWeatehrInfoDtosFromTextLines(ClassLoader.getSystemResourceAsStream(path), location, start, end);
    }
}
