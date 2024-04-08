package com.thelastofus.weatherapp.controller;

import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.LocationResponseApi;
import com.thelastofus.weatherapp.mapper.LocationMapper;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.service.LocationService;
import com.thelastofus.weatherapp.service.UserService;
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

    LocationService locationService;
    LocationMapper locationMapper;
    UserService userService;

    @ModelAttribute
    public void addAttributes(Principal principal,Model model){
        model.addAttribute("username",principal.getName());
    }

    @GetMapping()
    public String showHomePage(Model model,Principal principal){
        User user = userService.findByName(principal.getName());
        if (user.getLocations() != null || !user.getLocations().isEmpty()){
            model.addAttribute("weathers",locationService.showLocation(user));
        }
        return "main/home";
    }
    @PostMapping("/search")
    public String searchLocation(@RequestParam(name = "q") String q,Model model){
        if (q == null || q.isBlank()){
            return "redirect:/";
        }
        List<LocationResponseApi> locations = locationService.findLocationByName(q.replace(' ','_'));
        model.addAttribute("locations",locations);
        return "main/search";
    }
    @PostMapping()
    public String addLocation(@ModelAttribute("location_add") LocationDTO locationDTO,Model model,Principal principal){
        locationService.saveLocation(locationMapper.convertToLocation(locationDTO),principal);
        return "redirect:/";
    }
}
