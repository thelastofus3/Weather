package com.thelastofus.weatherapp.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thelastofus.weatherapp.util.UnixToLocalDateTimeDeserializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sys {
    @JsonProperty("sunrise")
    @JsonDeserialize(using = UnixToLocalDateTimeDeserializer.class)
    LocalTime sunrise;
    @JsonProperty("sunset")
    @JsonDeserialize(using = UnixToLocalDateTimeDeserializer.class)
    LocalTime sunset;
    @JsonProperty("country")
    String country;
}
