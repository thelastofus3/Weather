package com.thelastofus.weatherapp.mapper;

import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.UserDTO;
import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LocationMapper {

    ModelMapper modelMapper;

    public Location convertToLocation(LocationDTO locationDTO){
        return modelMapper.map(locationDTO,Location.class);
    }

}
