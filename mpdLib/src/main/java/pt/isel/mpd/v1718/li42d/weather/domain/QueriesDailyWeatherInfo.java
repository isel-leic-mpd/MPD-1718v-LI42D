package pt.isel.mpd.v1718.li42d.weather.domain;

import pt.isel.mpd.v1718.li42d.query.QueriesEager;

import java.util.Collection;

public class QueriesDailyWeatherInfo {
    public static DailyWeatherInfo getWithGreatestMaxTemp(Collection<DailyWeatherInfo> coll) {
        DailyWeatherInfo max = null;
        for (DailyWeatherInfo curr : coll) {
            if(max == null || curr.getMaxTemp() > max.getMaxTemp()) {
                max = curr;
            }
        }
        return max;
    }

    public static Iterable<DailyWeatherInfo> filterWithMaxTemperaturesAbove(Collection<DailyWeatherInfo> coll, int min) {
        return QueriesEager.filter(coll, dwi -> dwi.getMaxTemp() > min);

    }

    public static Iterable<DailyWeatherInfo> filterWithMaxTemperaturesBetween(Collection<DailyWeatherInfo> coll, int min, int max) {
        return QueriesEager.filter(coll, dwi -> dwi.getMaxTemp() > min && dwi.getMaxTemp() < max);
    }

    public static Iterable<Integer> mapToMaxTemperatures(Collection<DailyWeatherInfo> coll) {
        return QueriesEager.map(coll, dwi -> dwi.getMaxTemp());
    }
}


