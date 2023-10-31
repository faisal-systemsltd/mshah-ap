package com.example.demo.dtos;


import com.example.demo.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlayerRequestDTO {
    private String name;
    private int age;
    private int level;
    private String email;
    private Player.Gender gender;

    // Constructors, getters, and setters By LOMBOK
}
