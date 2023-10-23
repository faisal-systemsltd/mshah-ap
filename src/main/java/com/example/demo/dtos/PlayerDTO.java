package com.example.demo.dtos;

import lombok.Data;

@Data
public class PlayerDTO {
    private Long Id;
    private String playerName;
    private int age;
    private int level;
    private String email;
    private String gender;
}
