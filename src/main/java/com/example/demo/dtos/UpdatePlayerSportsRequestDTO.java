package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlayerSportsRequestDTO {
    private Set<Long> sportIds;

    // Constructors, getters, and setters
}

