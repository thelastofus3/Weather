package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.ForecastDTO;
import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.WeatherDTO;
import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OpenWeatherApiServiceImpl implements OpenWeatherApiService {
    static String findLocations = "http://api.openweathermap.org/geo/1.0/direct";
    static String findLocation = "https://api.openweathermap.org/data/2.5/weather";

    static String findForecast = "https://api.openweathermap.org/data/2.5/forecast";
    static String apiKey = "e5a9d8d7b1475fbb285e054b313b8852";

    @Override
    public List<LocationDTO> findLocationDTOByName(String q) {
        String uri = UriComponentsBuilder.fromHttpUrl(findLocations)
                .queryParam("q", q)
                .queryParam("limit", 4)
                .queryParam("appid", apiKey)
                .toUriString();

        Flux<LocationDTO> locations = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(LocationDTO.class);

        return locations.collectList().block();
    }

    @Override
    public List<WeatherDTO> showLocation(User user) {
        List<WeatherDTO> weatherList = new ArrayList<>();
        for (Location location : user.getLocations()){

            String uri = UriComponentsBuilder.fromHttpUrl(findLocation)
                    .queryParam("lat", location.getLatitude())
                    .queryParam("lon", location.getLongitude())
                    .queryParam("appid", apiKey)
                    .queryParam("units", "metric")
                    .toUriString();

            Flux<WeatherDTO> weather = WebClient.create()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .bodyToFlux(WeatherDTO.class);

            List<WeatherDTO> weatherDTOs = weather.collectList().block();
            for (WeatherDTO weatherDTO : weatherDTOs) {
                weatherDTO.setName(location.getName());
                weatherDTO.getCoordinates().setLatitude(location.getLatitude());
                weatherDTO.getCoordinates().setLongitude(location.getLongitude());
            }
            weatherList.addAll(weatherDTOs);
        }
        return weatherList;
    }

    @Override
    public List<ForecastDTO> findForecastForLocation(BigDecimal latitude, BigDecimal longitude) {
        String uri = UriComponentsBuilder.fromHttpUrl(findForecast)
                .queryParam("lat",  latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        Flux<ForecastDTO> forecast = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(ForecastDTO.class);


        System.out.println(forecast.collectList().block());
        return forecast.collectList().block();
    }

    @Override
    public List<ForecastDTO> forecastByDay(List<ForecastDTO> forecastList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<ForecastDTO> result = new ArrayList<>();
        forecastList.forEach(forecastDTO -> {
            forecastDTO.getForecast().forEach(forecast -> {
                if ("14:00".equals(forecast.getCurrentTime().format(formatter))) {
                    ForecastDTO newForecastDTO = new ForecastDTO();
                    newForecastDTO.setForecast(Collections.singletonList(forecast));
                    result.add(newForecastDTO);
                }
            });
        });
        return result;
    }
}
