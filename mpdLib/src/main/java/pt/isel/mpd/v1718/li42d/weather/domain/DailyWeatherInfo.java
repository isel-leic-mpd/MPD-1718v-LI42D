package pt.isel.mpd.v1718.li42d.weather.domain;

public class DailyWeatherInfo {
    private int maxTemp;
    private int minTemp;

    public DailyWeatherInfo(int maxTemp, int minTemp) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }
}
