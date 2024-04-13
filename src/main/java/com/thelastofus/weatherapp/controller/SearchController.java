package com.thelastofus.weatherapp.controller;

import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.service.OpenWeatherApiService;
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
public class SearchController {

    UserService userService;
    OpenWeatherApiService openWeatherApiService;

    @ModelAttribute
    public void addAttributes(Principal principal,Model model){
        model.addAttribute("username",principal.getName());
    }
    @GetMapping()
    public String showLocation(Model model, Principal principal){
        log.info("Show locations for user {}", principal.getName());
        User user = userService.findByName(principal.getName());
        if (user.getLocations() != null){
            model.addAttribute("weathers",openWeatherApiService.showLocation(user));
        }
        return "main/home";
    }
    @PostMapping("/search")
    public String searchLocation(@RequestParam(name = "q") String q, Model model){
        log.info("Find location for {}", q);
        if (q == null || q.isBlank()){
            return "redirect:/";
        }
        List<LocationDTO> locations = openWeatherApiService.findLocationDTOByName(replaceCharacter(q));
        log.info("Found locations for {}",q);
        if (locations.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("locations",locations);
        return "main/search";
    }
    private String replaceCharacter(String toReplace){
        return toReplace.replace(' ' , '_');
    }
}
