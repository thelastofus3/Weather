package com.thelastofus.weatherapp.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thelastofus.weatherapp.util.UnixToLocalDateDeserializer;
import com.thelastofus.weatherapp.util.UnixToLocalDateTimeDeserializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixToLocalDateTimeDeserializer.class)
    LocalTime currentTime;

    @JsonProperty("main")
    Main main;

    @JsonProperty("weather")
    List<Weather> weather;

    @JsonProperty("dt_txt")
    @JsonDeserialize(using = UnixToLocalDateDeserializer.class)
    LocalDate localDate;
}
