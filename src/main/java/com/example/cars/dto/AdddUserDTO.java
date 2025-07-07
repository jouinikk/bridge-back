package com.example.cars.dto;

import com.example.cars.entities.Role;
import lombok.Data;


@Data
public class AdddUserDTO{
    private String name;
    private String prenom;

    private String email;
    private String password;
    private Role role ;


//coach attributes
    private String telephone;
    private String specialite; // Butterfly, freestyle, etc.


//swimmer attributes
    private String niveau;
    private int anneeExperience;
    private Long groupe;
}
