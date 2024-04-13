package com.thelastofus.weatherapp.controller;

import com.thelastofus.weatherapp.dto.ForecastDTO;
import com.thelastofus.weatherapp.dto.entity.Forecast;
import com.thelastofus.weatherapp.service.OpenWeatherApiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/forecast")
@Slf4j
public class ForecastController {

    OpenWeatherApiService openWeatherApiService;

    @ModelAttribute
    public void addAttributes(Principal principal, Model model){
        model.addAttribute("username",principal.getName());
    }

    @GetMapping()
    public String showForecastPage(@RequestParam("latitude") BigDecimal latitude, @RequestParam("longitude") BigDecimal longitude,
                                   Model model){
        List<ForecastDTO> findhourlyForecast = openWeatherApiService.findForecastForLocation(latitude,longitude);
        List<ForecastDTO> findDailyForecast = openWeatherApiService.forecastByDay(findhourlyForecast);
        log.info("Hourly forecast {}" ,findhourlyForecast);
        log.info("Daily forecast {}",findDailyForecast);
        model.addAttribute("forecast",findhourlyForecast);
        model.addAttribute("forecastByDay",findDailyForecast);
        return "forecast/main";
    }
}
