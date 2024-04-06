package com.thelastofus.weatherapp.controller;

import com.thelastofus.weatherapp.dto.UserDTO;
import com.thelastofus.weatherapp.mapper.UserMapper;
import com.thelastofus.weatherapp.service.UserService;
import com.thelastofus.weatherapp.util.UserValidator;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class AuthenticationController {

    UserValidator userValidator;
    UserService userService;
    UserMapper userMapper;

    @GetMapping("/login")
    public String login(){

        return "auth/login";
    }
    @GetMapping("/registration")
    public String registration(@ModelAttribute("user")  UserDTO userDTO){
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid UserDTO userDTO,
                                            BindingResult bindingResult){
        userValidator.validate(userDTO,bindingResult);

        if (bindingResult.hasErrors()){
            return "/auth/registration";
        }
        userService.register(userMapper.converToUser(userDTO));

        return "redirect:/auth/login";
    }
}
