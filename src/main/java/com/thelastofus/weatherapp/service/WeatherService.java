package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.response.LocationApi;

import java.util.List;

public interface WeatherService {
    List<LocationApi> findLocationByName(String q);
}
