package com.thelastofus.weatherapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotBlank(message = "login should be empty")
    @Size(min = 5,max = 100,message = "login should be between 5 and 100 characters")
    @Column(name = "username",unique = true)
    String username;
    @Column(name = "password")
    @Size(min = 5,max = 100,message = "password should be between 5 and 100 characters")
    @NotBlank(message = "password should be empty")
    String password;
    @OneToMany
    @Builder.Default
    List<Location> locations = new ArrayList<>();
}
