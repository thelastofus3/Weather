package com.thelastofus.weatherapp.util;

import com.thelastofus.weatherapp.dto.UserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        UserDTO user = (UserDTO) o;
        boolean valid = user.getPassword() != null && user.getMatchingPassword() != null &&
                user.getPassword().equals(user.getMatchingPassword());
        if (!valid) {
            constraintValidatorContext.
                    buildConstraintViolationWithTemplate(constraintValidatorContext
                            .getDefaultConstraintMessageTemplate())
                    .addPropertyNode("matchingPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
