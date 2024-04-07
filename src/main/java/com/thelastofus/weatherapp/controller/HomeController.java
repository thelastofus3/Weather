package com.thelastofus.weatherapp.controller;

import com.thelastofus.weatherapp.response.LocationApi;
import com.thelastofus.weatherapp.service.WeatherService;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class HomeController {

    WeatherService weatherService;

    @GetMapping()
    public String showHomePage(Principal principal, Model model){
        model.addAttribute("username",principal.getName());
        weatherService.findLocationByName("fd");
        return "main/home";
    }
    @PostMapping("/search")
    public String searchLocation(Principal principal,@RequestParam(name = "q") String q,Model model){
        List<LocationApi> locations = weatherService.findLocationByName(q);
        model.addAttribute("locations",locations);
        return "main/search";
    }
}
