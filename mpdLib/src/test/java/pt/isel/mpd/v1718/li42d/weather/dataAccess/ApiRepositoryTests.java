package pt.isel.mpd.v1718.li42d.weather.dataAccess;

import org.junit.Test;
import pt.isel.mpd.v1718.li42d.weather.dataAccess.repositories.WorldWeatherOnlineCsvFileWeatherInfoRepository;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApiRepositoryTests {

    public static final String LOCATION = "Lisbon";
    public static final LocalDate START = LocalDate.of(2018, 02, 01);
    public static final LocalDate END = LocalDate.of(2018, 03, 10);

    @Test
    public void shouldGetDailyInfoDtosForALocationBetweenTwoDates() {
        // Arrange
        WeatherInfoRepository repository = new WorldWeatherOnlineWeatherInfoRepository();


        // Act
        final Collection<DailyWeatherInfoDto> dailyWeatherInfoDtos = repository.getDailyWeatherInfoDtos(LOCATION, START, END);

        // Assert
        assertNotNull(dailyWeatherInfoDtos);
        assertTrue(dailyWeatherInfoDtos.size() > 0);
    }
}
