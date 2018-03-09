package pt.isel.mpd.v1718.li42d.weather.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WeatherOperationsTest {
    WeatherInfoOperations wiOper = new WeatherInfoOperationsImpl(null);

    @Test
    public void shouldGetDailyWeatherInfoBetweenTwoDates() {
        // Arrange
        LocalDate start = null, end = null;

        // Act
        Collection<DailyWeatherInfo> dwi =  wiOper.getDailyWeatherInfo(start, end);


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
