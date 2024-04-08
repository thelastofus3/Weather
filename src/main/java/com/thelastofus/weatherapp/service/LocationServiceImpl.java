package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.WeatherDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LocationServiceImpl implements LocationService {

    static String findLocations = "http://api.openweathermap.org/geo/1.0/direct";
    static String apiKey = "e5a9d8d7b1475fbb285e054b313b8852";

    @Override
    public List<LocationDTO> findLocationByName(String q) {
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
    public WeatherDTO addLocation() {
        return null;
    }
}
