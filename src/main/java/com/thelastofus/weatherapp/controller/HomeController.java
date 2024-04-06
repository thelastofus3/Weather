package com.thelastofus.weatherapp.controller;

import com.thelastofus.weatherapp.util.UserValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class HomeController {


    @GetMapping("/")
    String showTimestamp(){
        return LocalDateTime.now().toString();
    }
}
