package pt.isel.mpd.v1718.li42d.weather.domain;

import pt.isel.mpd.v1718.li42d.weather.dataAccess.DailyWeatherInfoDto;
import pt.isel.mpd.v1718.li42d.weather.dataAccess.WeatherInfoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class WeatherInfoOperationsImpl implements WeatherInfoOperations {
    private WeatherInfoRepository weatherRepository;

    public WeatherInfoOperationsImpl(WeatherInfoRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public Collection<DailyWeatherInfo> getDailyWeatherInfo(String location, LocalDate start, LocalDate end) {
        Collection<DailyWeatherInfoDto> dwiDtos = weatherRepository.getDailyWeatherInfoDtos(location, start, end);
//        return convertTo(dwiDtos, new ObjectMapper<DailyWeatherInfoDto, DailyWeatherInfo>() {
//            @Override
//            public DailyWeatherInfo convert(DailyWeatherInfoDto dto) {
//                return new DailyWeatherInfo(dto.getMaxTemp(), dto.getMinTemp());
//            }
//        });
        return convertTo(dwiDtos, dto -> new DailyWeatherInfo(dto.getMaxTemp(), dto.getMinTemp(), dto.getDate()));

    }

    /**
     * TODO: Horrible code! Change it ASAP... Ughhhhhh!!!!!
     * @param dwiDtos
     * @return
     */
//    private Collection<DailyWeatherInfo> convertDailyWeatherInfoDtoToDailyWeatherInfo(Collection<DailyWeatherInfoDto> dwiDtos) {
//        Collection<DailyWeatherInfo> dwInfo = new ArrayList<>();
//        for (DailyWeatherInfoDto dwiDto : dwiDtos) {
//            dwInfo.add(new DailyWeatherInfo(dwiDto.getMaxTemp(), dwiDto.getMinTemp()));
//        }
//
//        return dwInfo;
//    }


    private <DOMAIN, DTO> Collection<DOMAIN> convertTo(Collection<DTO> dwiDtos, ObjectMapper<DTO, DOMAIN> mapper) {
        Collection<DOMAIN> domainColl = new ArrayList<>();
        for (DTO dto : dwiDtos) {
            domainColl.add(mapper.convert(dto));
        }

        return domainColl;
    }

    private interface ObjectMapper<DTO, DOMAIN> {
        DOMAIN convert(DTO t);
    }
}
