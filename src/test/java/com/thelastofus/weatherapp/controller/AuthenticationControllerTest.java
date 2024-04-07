package com.thelastofus.weatherapp.controller;

import com.thelastofus.weatherapp.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    @Test
    @SneakyThrows
    void register_nullFields(){
        mvc.perform(post("/auth/registration")
                .param("username","")
                .param("password","")
                .param("matchingPassword","")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is2xxSuccessful());
        verify(userService,never()).register(any());
    }
    @Test
    @SneakyThrows
    void register_shortPassword(){
        mvc.perform(post("/auth/registration")
                .param("username","goblin")
                .param("password","123")
                .param("matchingPassword","123")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is2xxSuccessful());
        verify(userService,never()).register(any());
    }
    @Test
    @SneakyThrows
    void register_successRegistration(){
        mvc.perform(post("/auth/registration")
                .param("username","goblin")
                .param("password","goldedit")
                .param("matchingPassword","goldedit")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        verify(userService,times(1)).register(any());
    }
    @Test
    @SneakyThrows
    void register_differentPassword(){
        mvc.perform(post("/auth/registration")
                .param("username","goblin")
                .param("password","platina")
                .param("matchingPassword","silver")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is2xxSuccessful());

        verify(userService,never()).register(any());
    }
    @Test
    @SneakyThrows
    void login(){
        mvc.perform(get("/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    @SneakyThrows
    void register(){
        mvc.perform(get("/auth/registration")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is2xxSuccessful());
    }
}
