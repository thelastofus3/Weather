package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.LocationResponseApi;
import com.thelastofus.weatherapp.dto.WeatherDTO;
import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;

import java.security.Principal;
import java.util.List;

public interface LocationService {
    List<LocationResponseApi> findLocationByName(String q);

    List<WeatherDTO> showLocation(User user);

    void saveLocation(Location location, Principal principal);
}
