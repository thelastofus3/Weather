package com.thelastofus.weatherapp.controller;


import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.service.LocationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class HomeController {

    LocationService locationService;


    @ModelAttribute
    public void addAttributes(Principal principal,Model model){
        model.addAttribute("username",principal.getName());
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
