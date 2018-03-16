package pt.isel.mpd.v1718.li42d.weather.domain;

import org.junit.Test;
import pt.isel.mpd.v1718.li42d.weather.dataAccess.repositories.InMemoryWeatherInfoRepository;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WeatherOperationsTest {
    WeatherInfoOperations wiOper = new WeatherInfoOperationsImpl(
            new InMemoryWeatherInfoRepository()
    );

    @Test
    public void shouldGetDailyWeatherInfoBetweenTwoDates() {
        // Arrange
        LocalDate start = LocalDate.of(2018, 02, 01), end = LocalDate.of(2018, 03, 10);

        // Act
        Collection<DailyWeatherInfo> dwi =  wiOper.getDailyWeatherInfo("Lisbon", start, end);


        // Assert
        assertNotNull(dwi);
        assertTrue(dwi.size() > 0);

        assertDailyWeatherInfoCollectionContent(dwi);
    }

    private void assertDailyWeatherInfoCollectionContent(Collection<DailyWeatherInfo> dwi) {
        for (DailyWeatherInfo dailyWeatherInfo : dwi) {
            final int maxTemp = dailyWeatherInfo.getMaxTemp();
            assertTrue(maxTemp > -20 && maxTemp < 50);
            final int minTemp = dailyWeatherInfo.getMinTemp();
            assertTrue(minTemp > -20 && minTemp < 50);
            assertTrue(maxTemp >= minTemp);
        }
    }
}
