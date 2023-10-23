package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SportsResponseDTO {
    private String sportName;
    private List<PlayerDTO> players;



    // Constructors, getters, and setters
}

