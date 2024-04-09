package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.ForecastDTO;
import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.WeatherDTO;
import com.thelastofus.weatherapp.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface OpenWeatherApiService {

    List<LocationDTO> findLocationDTOByName(String q);

    List<WeatherDTO> showLocation(User user);

    List<ForecastDTO> findForecastForLocation(BigDecimal latitude,BigDecimal longitude);

    List<ForecastDTO> forecastByDay(List<ForecastDTO> forecastList);
}
