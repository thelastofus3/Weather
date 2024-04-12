package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.LocationDTO;
import lombok.experimental.NonFinal;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OpenWeatherApiServiceTest {

    @Autowired
    MockMvc mvc;

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
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setName("Test Location");
        List<LocationDTO> locationDTOList = Collections.singletonList(locationDTO);

        when(service.findLocationDTOByName(anyString())).thenReturn(locationDTOList);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(LocationDTO.class)).thenReturn(Flux.just(locationDTO));

        List<LocationDTO> result = service.findLocationDTOByName("test");

        assertEquals(locationDTOList, result);
    }
}