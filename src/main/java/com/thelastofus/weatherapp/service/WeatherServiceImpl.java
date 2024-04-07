package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.response.LocationApi;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class WeatherServiceImpl implements WeatherService {

    RestTemplate restTemplate;

    static String findLocations = "http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid=e5a9d8d7b1475fbb285e054b313b8852";
    @Override
    public List<LocationApi> findLocationByName(String q) {
        ResponseEntity<List<LocationApi>> response = restTemplate.exchange(
                findLocations,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LocationApi>>() {});
        List<LocationApi> locationApis = response.getBody();
        System.out.println(locationApis);
        return locationApis;
    }
}
