package com.thelastofus.weatherapp.controller;


import com.thelastofus.weatherapp.dto.LocationDTO;

import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.service.LocationService;
import com.thelastofus.weatherapp.service.OpenWeatherApiService;
import com.thelastofus.weatherapp.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class HomeController {

    LocationService locationService;
    UserService userService;
    OpenWeatherApiService openWeatherApiService;

    @ModelAttribute
    public void addAttributes(Principal principal,Model model){
        model.addAttribute("username",principal.getName());
    }

    @GetMapping()
    public String showHomePage(Model model,Principal principal){
        User user = userService.findByName(principal.getName());
        if (user.getLocations() != null || !user.getLocations().isEmpty()){
            model.addAttribute("weathers",openWeatherApiService.showLocation(user));
        }
        return "main/home";
    }
    @PostMapping("/search")
    public String searchLocation(@RequestParam(name = "q") String q,Model model){
        if (q == null || q.isBlank()){
            return "redirect:/";
        }
        List<LocationDTO> locations = openWeatherApiService.findLocationDTOByName(q.replace(' ','_'));
        model.addAttribute("locations",locations);
        return "main/search";
    }
    @PostMapping()
    public String addLocation(@ModelAttribute("location_add") Location location, Principal principal){
        locationService.saveLocation(location,principal);
        return "redirect:/";
    }
    @DeleteMapping()
    public String deleteLocation(@RequestParam("latitude") BigDecimal latitude, @RequestParam("longitude") BigDecimal longitude,
                                 Principal principal){
        List<Location> locations = locationService.findLocationsByCoordinates(latitude,longitude,principal);
        if (!locations.isEmpty()){
            locationService.deleteLocation(locations.getFirst());
        }
        return "redirect:/";
    }
}
