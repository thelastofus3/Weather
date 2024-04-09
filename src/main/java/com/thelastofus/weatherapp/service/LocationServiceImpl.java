package com.thelastofus.weatherapp.service;



import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.repository.LocationRepository;
import com.thelastofus.weatherapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;

import java.util.List;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LocationServiceImpl implements LocationService {

    LocationRepository locationRepository;
    UserRepository userRepository;


    @Override
    @Transactional
    public void saveLocation(Location location, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        location.setOwner(user);
        locationRepository.save(location);
    }

    @Override
    @Transactional
    public void deleteLocation(Location location) {
        System.out.println(location.getName());
        locationRepository.delete(location);
    }


    @Override
    public List<Location> findLocationsByCoordinates(BigDecimal latitude, BigDecimal longitude, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        return locationRepository.findByLatitudeAndLongitudeAndOwner(latitude,longitude,user);
    }

}
