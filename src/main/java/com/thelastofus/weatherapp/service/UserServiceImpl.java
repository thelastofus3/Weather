package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.UserDTO;
import com.thelastofus.weatherapp.exception.UserNotFoundException;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> checkUserExist(UserDTO userDTO)  {
        return userRepository.findByUsername(userDTO.getUsername());
    }

    @Override
    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new UserNotFoundException("User: " + name + "not found"));
    }
}
