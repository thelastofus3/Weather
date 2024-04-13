package com.thelastofus.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thelastofus.weatherapp.dto.entity.Forecast;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;



@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDTO {

    @JsonProperty("list")
    List<Forecast> forecast;

}
