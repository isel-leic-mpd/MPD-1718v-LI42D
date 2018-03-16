package pt.isel.mpd.v1718.li42d.weather.domain;

import java.util.ArrayList;
import java.util.Collection;

public class Queries {
    public static DailyWeatherInfo getWithGreatestMaxTemp(Collection<DailyWeatherInfo> coll) {
        DailyWeatherInfo max = null;
        for (DailyWeatherInfo curr : coll) {
            if(max == null || curr.getMaxTemp() > max.getMaxTemp()) {
                max = curr;
            }
        }
        return max;
    }

    public static Collection<DailyWeatherInfo> filterWithMaxTemperauresAbove(Collection<DailyWeatherInfo> coll, int min) {
        Collection<DailyWeatherInfo> result = new ArrayList<>();
        for (DailyWeatherInfo curr : coll) {
            if(curr.getMaxTemp() > min)
                result.add(curr);
        }
        return result;
    }

    public static Collection<DailyWeatherInfo> filterWithMaxTemperauresBetween(Collection<DailyWeatherInfo> coll, int min, int max) {
        Collection<DailyWeatherInfo> result = new ArrayList<>();
        for (DailyWeatherInfo curr : coll) {
            if(curr.getMaxTemp() > min && curr.getMaxTemp() < max)
                result.add(curr);
        }
        return result;
    }
}
