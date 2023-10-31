package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class CreateSportsRequestDTO {

        private String name;
        private String description;

}
