package com.thelastofus.weatherapp.dto;


import com.thelastofus.weatherapp.util.PasswordMatches;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PasswordMatches
@Builder
public class UserDTO {
    @NotBlank(message = "Login should not be empty")
    @Size(min = 5,max = 100,message = "Login should be between 5 and 100 characters")
    String username;
    @NotBlank(message = "Password should not be empty")
    @Size(min = 5,max = 100,message = "Password should be between 5 and 100 characters")
    String password;
    String matchingPassword;
}
