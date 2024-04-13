package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.ForecastDTO;
import com.thelastofus.weatherapp.dto.LocationDTO;
import com.thelastofus.weatherapp.dto.WeatherDTO;
import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.model.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class OpenWeatherApiServiceTest {

    @MockBean
    OpenWeatherApiService service;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;


    @Test
    public void testFindLocationDTOByName()  {
        var locationDTO = new LocationDTO();
        locationDTO.setName("Test Location");
        List<LocationDTO> locationDTOList = Collections.singletonList(locationDTO);

        when(service.findLocationDTOByName(anyString())).thenReturn(locationDTOList);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(LocationDTO.class)).thenReturn(Flux.just(locationDTO));

        List<LocationDTO> result = service.findLocationDTOByName("test");

        assertEquals(locationDTOList, result);
    }
    @Test
    public void testShowLocation() {
        var user = new User();
        var location = new Location();
        location.setName("Test Location");
        location.setLatitude(new BigDecimal("40.7128"));
        location.setLongitude(new BigDecimal("74.0060"));
        user.setLocations(Collections.singletonList(location));

        var weatherDTO = new WeatherDTO();
        weatherDTO.setName("Test Weather");
        List<WeatherDTO> weatherDTOList = Collections.singletonList(weatherDTO);

        when(service.showLocation(any(User.class))).thenReturn(weatherDTOList);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(WeatherDTO.class)).thenReturn(Flux.just(weatherDTO));

        List<WeatherDTO> result = service.showLocation(user);
        assertEquals(weatherDTOList, result);
    }

    @Test
    public void testFindForecastForLocation() {
        var forecastDTO = new ForecastDTO();
        List<ForecastDTO> forecastDTOList = Collections.singletonList(forecastDTO);

        when(service.findForecastForLocation(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(forecastDTOList);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(ForecastDTO.class)).thenReturn(Flux.just(forecastDTO));

        List<ForecastDTO> result = service.findForecastForLocation(new BigDecimal("40.7128"), new BigDecimal("74.0060"));
        assertEquals(forecastDTOList, result);
    }
}