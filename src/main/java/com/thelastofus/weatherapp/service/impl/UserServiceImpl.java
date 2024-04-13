package com.thelastofus.weatherapp.service.impl;

import com.thelastofus.weatherapp.dto.UserDTO;
import com.thelastofus.weatherapp.exception.UserNotFoundException;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.repository.UserRepository;
import com.thelastofus.weatherapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
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
    public User findByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User: " + username + "not found"));
    }
}
