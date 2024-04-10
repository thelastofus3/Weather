package com.thelastofus.weatherapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class OpenWeatherApiServiceTest {

    @Autowired
    private OpenWeatherApiService openWeatherApiService;


}