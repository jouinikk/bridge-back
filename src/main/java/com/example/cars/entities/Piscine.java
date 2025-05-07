package com.example.cars.entities;

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
public class Piscine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String adresse;
    private String ville;
    private String codePostal;
    private String telephone;
    private String email;
    
    private int nombreLignesEau; // Number of swimming lanes
    
    // Geolocation data for map integration
    private double latitude;
    private double longitude;
    
    @OneToMany(mappedBy = "piscine")
    private List<LigneEau> lignesEau;
    
    // Relationship with other entities can be added as needed
}