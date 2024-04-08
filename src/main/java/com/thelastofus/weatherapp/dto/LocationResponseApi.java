package com.thelastofus.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResponseApi {

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
