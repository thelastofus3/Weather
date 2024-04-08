package com.thelastofus.weatherapp.controller;

import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.service.LocationService;
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

    @ModelAttribute
    public void addAttributes(Principal principal,Model model){
        model.addAttribute("username",principal.getName());
    }

    @GetMapping()
    public String showHomePage(){
        return "main/home";
    }
    @PostMapping("/search")
    public String searchLocation(@RequestParam(name = "q") String q,Model model){
        if (q == null || q.isBlank()){
            return "redirect:/";
        }
        List<LocationDTO> locations = locationService.findLocationByName(q.replace(' ','_'));
        model.addAttribute("locations",locations);
        return "main/search";
    }
    @PostMapping()
    public String addLocation(){
        locationService.addLocation();
        return "main/home";
    }

}
