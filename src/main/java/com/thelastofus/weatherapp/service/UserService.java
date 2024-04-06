package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.UserDTO;
import com.thelastofus.weatherapp.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> checkUserExist(UserDTO userDTO);
    void register(User user);
}
