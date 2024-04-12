package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.ForecastDTO;
import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.WeatherDTO;
import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OpenWeatherApiServiceImpl implements OpenWeatherApiService {

    @Value("${weather.find.locations}")
    @NonFinal
    String findLocations;

    @Value("${weather.find.location}")
    @NonFinal
    String findLocation;

    @Value("${weather.find.forecast}")
    @NonFinal
    String findForecast;

    @Value("${api.key}")
    @NonFinal
    String apiKey;

    String forecastForTime = "14:00";

    WebClient webClient = WebClient.create();

    @Override
    public List<LocationDTO> findLocationDTOByName(String q) {
        return getDataFromAPI(q, null, findLocations, LocationDTO.class);
    }

    @Override
    public List<WeatherDTO> showLocation(User user) {
        return user.getLocations().stream()
                .flatMap(location -> {
                    List<WeatherDTO> weatherDTOs = getDataFromAPI(location.getLatitude(), location.getLongitude(), findLocation, WeatherDTO.class);
                    weatherDTOs.forEach(weatherDTO -> {
                        weatherDTO.setName(location.getName());
                        weatherDTO.getCoordinates().setLatitude(location.getLatitude());
                        weatherDTO.getCoordinates().setLongitude(location.getLongitude());
                    });
                    return weatherDTOs.stream();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ForecastDTO> findForecastForLocation(BigDecimal latitude, BigDecimal longitude) {
        return getDataFromAPI(latitude, longitude, findForecast, ForecastDTO.class);
    }

    @Override
    public List<ForecastDTO> forecastByDay(List<ForecastDTO> forecastList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return forecastList.stream()
                .flatMap(forecastDTO -> forecastDTO.getForecast().stream())
                .filter(forecast -> forecastForTime.equals(forecast.getCurrentTime().format(formatter)))
                .map(forecast -> {
                    ForecastDTO newForecastDTO = new ForecastDTO();
                    newForecastDTO.setForecast(Collections.singletonList(forecast));
                    return newForecastDTO;
                })
                .collect(Collectors.toList());
    }

    private <T> List<T> getDataFromAPI(Object param1, Object param2, String url, Class<T> type) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric");

        if (param1 instanceof String) {
            builder.queryParam("q", param1).queryParam("limit", 4);
        } else if (param1 instanceof BigDecimal) {
            builder.queryParam("lat", param1).queryParam("lon", param2);
        }

        return webClient.get()
                .uri(builder.toUriString())
                .retrieve()
                .bodyToFlux(type).collectList().block();
    }
}
