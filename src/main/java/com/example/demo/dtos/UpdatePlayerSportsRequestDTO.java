package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdatePlayerSportsRequestDTO {
    private List<Long> sportIds;

    // Constructors, getters, and setters
}

