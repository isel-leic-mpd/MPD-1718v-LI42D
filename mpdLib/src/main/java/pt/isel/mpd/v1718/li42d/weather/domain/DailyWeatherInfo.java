package pt.isel.mpd.v1718.li42d.weather.domain;

import java.time.LocalDate;

public class DailyWeatherInfo {
    private int maxTemp;
    private int minTemp;
    private LocalDate date;


    public DailyWeatherInfo(int maxTemp, int minTemp, LocalDate date) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }
}
