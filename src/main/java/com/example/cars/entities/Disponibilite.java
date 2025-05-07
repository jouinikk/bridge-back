package com.example.cars.entities;

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
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private DayOfWeek jourSemaine;
    
    private LocalTime heureOuverture;
    private LocalTime heureFermeture;
    
    @ManyToOne
    @JoinColumn(name = "piscine_id")
    private Piscine piscine;
    
    @ManyToOne
    @JoinColumn(name = "ligne_eau_id")
    private LigneEau ligneEau;
    
    private boolean estDisponible;  // True if available, false if closed or under maintenance
    
    private String raisonIndisponibilite; // Optional reason if unavailable
}