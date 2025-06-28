package com.example.cars.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)  // Add this line
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private DayOfWeek jourSemaine;
    
    private LocalTime heureOuverture;
    private LocalTime heureFermeture;
    
    @JsonIgnoreProperties("lignesEau")
    @ManyToOne
    @JoinColumn(name = "piscine_id")
    private Piscine piscine;
    
    @JsonIgnoreProperties({"piscine", "seances"})
    @ManyToOne
    @JoinColumn(name = "ligne_eau_id")
    private LigneEau ligneEau;
    
    private boolean estDisponible;  // True if available, false if closed or under maintenance
    
    private String raisonIndisponibilite; // Optional reason if unavailable
}