package com.example.cars.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter // This Lombok annotation should create setId()
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Centre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String adresse;
    private String ville;
    
    @OneToMany(mappedBy = "centre", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Piscine> piscines;
}