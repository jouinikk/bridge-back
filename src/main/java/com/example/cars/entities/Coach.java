package com.example.cars.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite; // Butterfly, freestyle, etc.
    private int anneeExperience;
    
    @JsonIgnoreProperties({"coach", "nageurs", "seances"})
    @OneToMany(mappedBy = "coach")
    private List<Groupe> groupes;
    
    @JsonIgnoreProperties({"coach", "groupe"})
    @OneToMany(mappedBy = "coach")
    private List<Seance> seances;
}