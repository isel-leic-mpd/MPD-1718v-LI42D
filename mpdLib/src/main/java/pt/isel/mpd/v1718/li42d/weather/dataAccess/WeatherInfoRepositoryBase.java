package pt.isel.mpd.v1718.li42d.weather.dataAccess;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public abstract class WeatherInfoRepositoryBase implements WeatherInfoRepository {


    @Override
    public Collection<DailyWeatherInfoDto> getDailyWeatherInfoDtos(String location, LocalDate start, LocalDate end) {
        InputStream inputStream = getStream(location, start, end);

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        Collection<DailyWeatherInfoDto> coll = new ArrayList<>();
        try {
            String line;

            for (int i = 0; i < 8; i++) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    final String[] split = line.split(",");
                    if (split.length == 9) {
                        LocalDate date = LocalDate.parse(split[0]);
                        if (date.isAfter(start) && date.isBefore(end)) {
                            coll.add(new DailyWeatherInfoDto(Integer.parseInt(split[1]), Integer.parseInt(split[3]), date));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return coll;


    }

    protected abstract InputStream getStream(String location, LocalDate start, LocalDate end);
}
