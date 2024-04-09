package com.thelastofus.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thelastofus.weatherapp.dto.entity.Forecast;
import lombok.Data;
import lombok.Getter;

import java.util.List;


@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDTO {

    @JsonProperty("list")
    List<Forecast> forecast;

}
