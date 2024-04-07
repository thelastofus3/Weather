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
    @NotBlank(message = "Login should not be empty")
    @Size(min = 5,max = 100,message = "Login should be between 5 and 100 characters")
    @Column(name = "username",unique = true)
    String username;
    @Column(name = "password")
    @Size(min = 5,max = 100,message = "Password should be between 5 and 100 characters")
    @NotBlank(message = "Password should not be empty")
    String password;
    @OneToMany
    @Builder.Default
    List<Location> locations = new ArrayList<>();
}
