package com.example.cars.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String telephone;
    private String specialite; // Butterfly, freestyle, etc.
    private int anneeExperience;
    private String niveau;
}