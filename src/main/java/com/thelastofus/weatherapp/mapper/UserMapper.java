package com.thelastofus.weatherapp.mapper;

import com.thelastofus.weatherapp.dto.UserDTO;
import com.thelastofus.weatherapp.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserMapper {

    ModelMapper modelMapper;

    public User converToUser(UserDTO userDTO){
        return modelMapper.map(userDTO,User.class);
    }
}
