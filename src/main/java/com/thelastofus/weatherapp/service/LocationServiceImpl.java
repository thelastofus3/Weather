package com.thelastofus.weatherapp.service;


import com.thelastofus.weatherapp.dto.LocationResponseApi;
import com.thelastofus.weatherapp.dto.WeatherDTO;
import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.repository.LocationRepository;
import com.thelastofus.weatherapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LocationServiceImpl implements LocationService {

    LocationRepository locationRepository;
    UserRepository userRepository;

    static String findLocations = "http://api.openweathermap.org/geo/1.0/direct";
    static String findLocation = "https://api.openweathermap.org/data/2.5/weather";
    static String apiKey = "e5a9d8d7b1475fbb285e054b313b8852";

    @Override
    public List<LocationResponseApi> findLocationByName(String q) {
        String uri = UriComponentsBuilder.fromHttpUrl(findLocations)
                .queryParam("q", q)
                .queryParam("limit", 4)
                .queryParam("appid", apiKey)
                .toUriString();

        Flux<LocationResponseApi> locations = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(LocationResponseApi.class);


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

            weatherList.addAll(weather.collectList().block());
        }
        return weatherList;
    }

    @Override
    @Transactional
    public void saveLocation(Location location, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        location.setOwner(user);
        locationRepository.save(location);
    }
}
