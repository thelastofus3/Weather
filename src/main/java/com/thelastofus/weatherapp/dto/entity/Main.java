package com.thelastofus.weatherapp.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {

    @JsonProperty("temp")
    Double temperature ;
    @JsonProperty("feels_like")
    Double feelsLike;
    @JsonProperty("temp_min")
    Double minTemperature;
    @JsonProperty("temp_max")
    Double maxTemperature;
    @JsonProperty("humidity")
    Integer humidity;
    @JsonProperty("pressure")
    Integer pressure;
}
