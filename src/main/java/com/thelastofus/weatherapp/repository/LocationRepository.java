package com.thelastofus.weatherapp.repository;

import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {

    List<Location> findByLatitudeAndLongitudeAndOwner(BigDecimal latitude, BigDecimal longitude, User user);

}
