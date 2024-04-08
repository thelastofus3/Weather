package com.thelastofus.weatherapp.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Clouds {
    @JsonProperty("all")
    Integer all;


}
