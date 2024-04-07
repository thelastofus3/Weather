package com.thelastofus.weatherapp.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationApi {

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

    @Override
    public String toString() {
        return "LocationApi{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
