package com.thelastofus.weatherapp.service;

import com.thelastofus.weatherapp.dto.UserDTO;
import com.thelastofus.weatherapp.mapper.UserMapper;
import com.thelastofus.weatherapp.model.User;
import com.thelastofus.weatherapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Testcontainers
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Container
    private static final MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql:latest"));

    @DynamicPropertySource
    static void configurationProperties(DynamicPropertyRegistry registry){
                registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
                registry.add("spring.datasource.username", mySQLContainer::getUsername);
                registry.add("spring.datasource.password", mySQLContainer::getPassword);
                registry.add("spring.jpa.generate-ddl",() -> true);
    }

    @Test
    void register_successSaveUserInDatabase() {
        UserDTO userDTO = UserDTO.builder()
                .username("goblin")
                .password("password")
                .matchingPassword("password").build();

        userService.register(userMapper.convertToUser(userDTO));

        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        assertTrue(user.isPresent(),"Expected user to be present in the database");

        user.ifPresent(u -> {
            assertEquals(userDTO.getUsername(),u.getUsername(),"Username should be equals");
            assertNotEquals(userDTO.getPassword(),u.getPassword(),"Password should not be equals");
        });
    }

    @Test
    void register_failedSaveSameUserTwice(){
        UserDTO userDTO = UserDTO.builder()
                .username("goblin")
                .password("password")
                .matchingPassword("password").build();

        userService.register(userMapper.convertToUser(userDTO));

        assertThrows(Exception.class, () -> userService.register(userMapper.convertToUser(userDTO)));

        assertEquals(1,userRepository.findAll().size(),"User should be save once");

    }
}