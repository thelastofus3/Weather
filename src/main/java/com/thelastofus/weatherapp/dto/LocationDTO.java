package com.thelastofus.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO {

    @JsonProperty("name")
    String name;

    @JsonProperty("lat")
    BigDecimal latitude;

    @JsonProperty("lon")
    BigDecimal longitude;

    @JsonProperty("country")
    String country;

    @JsonProperty("state")
    String state;

}
