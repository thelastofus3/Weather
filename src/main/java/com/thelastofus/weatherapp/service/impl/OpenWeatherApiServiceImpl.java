package com.thelastofus.weatherapp.service.impl;

import com.thelastofus.weatherapp.dto.ForecastDTO;
import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.WeatherDTO;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.service.OpenWeatherApiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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

    @Value("14:00")
    @NonFinal
    String forecastForTime;

    WebClient webClient;

    @Override
    public List<LocationDTO> findLocationDTOByName(String q) {
        return getDataFromAPI(q, null, findLocations, LocationDTO.class);
    }

    @Override
    public List<WeatherDTO> showLocations(User user) {
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
        String builder = url
                + "?appid=" + apiKey
                + "&units=" + "metric";
        if (param1 instanceof String) {
            builder += "&q=" + param1
                    + "&limit=" + 4;
        } else if (param1 instanceof BigDecimal) {
            builder += "&lat=" + param1
                    + "&lon=" + param2;
        }

        return webClient.get()
                .uri(URI.create(builder))
                .retrieve()
                .bodyToFlux(type).collectList().block();
    }
}