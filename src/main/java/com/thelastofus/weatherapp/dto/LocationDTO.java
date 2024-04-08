package com.thelastofus.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.util.PasswordMatches;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PasswordMatches
@Builder
public class LocationDTO {

    String name;

    User owner = new User();

    BigDecimal latitude;

    BigDecimal longitude;

}
