package com.thelastofus.weatherapp.util;

import com.thelastofus.weatherapp.dto.UserDTO;
import com.thelastofus.weatherapp.exception.UserAlreadyExistException;
import com.thelastofus.weatherapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserValidator implements Validator {

    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO) target;
        if (userService.checkUserExist(user).isPresent()){
            errors.rejectValue("username","","An account for %s already exists".formatted(user.getUsername()));
        }
    }
}
