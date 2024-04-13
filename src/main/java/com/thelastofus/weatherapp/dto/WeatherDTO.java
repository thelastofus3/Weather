package com.thelastofus.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thelastofus.weatherapp.dto.entity.*;
import com.thelastofus.weatherapp.util.UnixToLocalDateTimeDeserializer;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

    @JsonProperty("coord")
    Coordinates coordinates;

    @JsonProperty("name")
    String name;

    @JsonProperty("weather")
    List<Weather> weather;

    @JsonProperty("main")
    Main main;

    @JsonProperty("wind")
    Wind wind;

    @JsonProperty("clouds")
    Clouds clouds;

    @JsonProperty("sys")
    Sys sys;

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixToLocalDateTimeDeserializer.class)
    LocalTime currentTime;

}
