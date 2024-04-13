package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.repository.LocationRepository;
import com.thelastofus.weatherapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationServiceTest {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationService locationService;

    @Container
    static final MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql:latest"));

    @DynamicPropertySource
    static void configurationProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.jpa.generate-ddl",() -> true);
    }

    @Test
    public void save_successSaveLocationInDatabase() {
        var user = new User(1,"Goblin","password",new ArrayList<>());

        userRepository.save(user);

        var location = new Location();
        location.setLatitude(new BigDecimal("40.712776"));
        location.setLongitude(new BigDecimal("-74.005974"));
        location.setOwner(user);

        locationRepository.save(location);
        assertEquals(1,userRepository.findAll().size(),"Location should be save once");
    }

    @Test
    public void delete_successDeleteLocationFromDatabase(){
        var user = new User(1,"Goblin","password",new ArrayList<>());

        userRepository.save(user);

        var location = new Location();
        location.setLatitude(new BigDecimal("40.712776"));
        location.setLongitude(new BigDecimal("-74.005974"));
        location.setOwner(user);

        var savedLocation = locationRepository.save(location);

        locationRepository.delete(savedLocation);

        assertFalse(locationRepository.findById(savedLocation.getId()).isPresent());
    }
}
