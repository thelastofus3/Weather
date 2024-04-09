package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.model.Location;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface LocationService {

    void saveLocation(Location location, Principal principal);

    void deleteLocation(Location location);

    List<Location> findLocationsByCoordinates(BigDecimal latitude, BigDecimal longitude,Principal principal);

}
