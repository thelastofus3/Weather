package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.WeatherDTO;
import com.thelastofus.weatherapp.model.User;

import java.util.List;

public interface OpenWeatherApiService {

    List<LocationDTO> findLocationDTOByName(String q);

    List<WeatherDTO> showLocation(User user);
}
