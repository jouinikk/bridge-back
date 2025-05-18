package com.example.cars.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = {"coach", "groupe", "ligneEau", "piscine"}) // Exclude complex objects from toString
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
    
    @JsonIgnoreProperties({"groupes", "seances"})
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;
    
    @JsonIgnoreProperties({"seances", "nageurs", "coach"})
    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;
    
    @JsonIgnoreProperties({"seances", "piscine"})
    @ManyToOne
    @JoinColumn(name = "ligne_eau_id")
    private LigneEau ligneEau;
    
    @JsonIgnoreProperties({"lignesEau", "seances"})
    @ManyToOne
    @JoinColumn(name = "piscine_id")
    private Piscine piscine;
    
    // Status of the session
    @Enumerated(EnumType.STRING)
    private StatutSeance statut;
    
    private Integer nombreMaxParticipants;
    private String niveau; // Beginner, Intermediate, Advanced
    
    private boolean estDisponible = true;
    
    public enum StatutSeance {
        PLANIFIEE, EN_COURS, TERMINEE, ANNULEE
    }
}