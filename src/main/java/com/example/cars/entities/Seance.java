package com.example.cars.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titre;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String objectifs;
    private String typeEntrainement; // Endurance, sprint, technique, etc.
    
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;
    
    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;
    
    @ManyToOne
    @JoinColumn(name = "ligne_eau_id")
    private LigneEau ligneEau;
    
    // Status of the session
    @Enumerated(EnumType.STRING)
    private StatutSeance statut;
    
    public enum StatutSeance {
        PLANIFIEE, EN_COURS, TERMINEE, ANNULEE
    }
}