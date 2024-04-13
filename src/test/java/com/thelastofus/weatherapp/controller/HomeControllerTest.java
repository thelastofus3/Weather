package com.thelastofus.weatherapp.controller;

import com.thelastofus.weatherapp.model.Location;
import com.thelastofus.weatherapp.service.LocationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @MockBean
    private LocationService locationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithMockUser(username = "testUser",password = "testUser")
    public void add_successAddLocation()  {
        Location location = new Location();
        location.setLatitude(new BigDecimal("40.712776"));
        location.setLongitude(new BigDecimal("-74.005974"));

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .param("latitude", location.getLatitude().toString())
                        .param("longitude", location.getLongitude().toString())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(locationService, times(1)).saveLocation(any(Location.class), any(Principal.class));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "testUser",password = "testUser")
    public void delete_successDeleteLocation() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        mockMvc.perform(MockMvcRequestBuilders.delete("/")
                        .principal(principal)
                        .with(csrf())
                        .param("latitude", "40.712776")
                        .param("longitude", "-74.005974")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(locationService, times(1)).findLocationsByCoordinates(any(BigDecimal.class), any(BigDecimal.class), any(Principal.class));
    }

}