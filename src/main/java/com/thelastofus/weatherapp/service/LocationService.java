package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.WeatherDTO;

import java.util.List;

public interface LocationService {
    List<LocationDTO> findLocationByName(String q);

    WeatherDTO addLocation();
}
