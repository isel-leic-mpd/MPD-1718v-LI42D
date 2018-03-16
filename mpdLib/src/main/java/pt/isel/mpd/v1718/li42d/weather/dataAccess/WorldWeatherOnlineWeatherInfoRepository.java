package pt.isel.mpd.v1718.li42d.weather.dataAccess;

import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Collection;

public class WorldWeatherOnlineWeatherInfoRepository extends WeatherInfoRepositoryBase {
    private static final String KEY_FILE = "key.txt";
    final String URL_TEMPLATE = "http://api.worldweatheronline.com/premium/v1/past-weather.ashx?q={0}&format=csv&key={1}&date={2}&enddate={3}";
    private static final String KEY;

    static {
        URL keyFile = ClassLoader.getSystemResource(KEY_FILE);
        if (keyFile == null) {
            throw new IllegalStateException(
                    "YOU MUST GOT a KEY in developer.worldweatheronline.com and place it in src/main/resources/key.txt");
        } else {
            try {
                InputStream keyStream = keyFile.openStream();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(keyStream))) {
                    KEY = reader.readLine();
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }


    @Override
    public Collection<DailyWeatherInfoDto> getDailyWeatherInfoDtos(String location, LocalDate start, LocalDate end) {
        String url = MessageFormat.format(URL_TEMPLATE, location, KEY, start.toString(), end.toString());
        System.out.println(url);
        try {
            return super.createWeatehrInfoDtosFromTextLines(new URL(url).openStream(), location, start, end);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
