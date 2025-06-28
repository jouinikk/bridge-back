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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_id")
    @JsonIgnoreProperties({"groupes", "seances"})
    private Coach coach;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupe_id")
    @JsonIgnoreProperties({"seances", "nageurs", "coach"})
    private Groupe groupe;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ligne_eau_id")
    @JsonIgnoreProperties({"seances"})
    private LigneEau ligneEau;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "piscine_id")
    @JsonIgnoreProperties({"lignesEau", "seances"})
    private Piscine piscine;
    
    @Enumerated(EnumType.STRING)
    private StatutSeance statut;
    
    private Integer nombreMaxParticipants;
    private String niveau; // Beginner, Intermediate, Advanced
    
    @Builder.Default
    private boolean estDisponible = true;
    
    public enum StatutSeance {
        PLANIFIEE, EN_COURS, TERMINEE, ANNULEE
    }
}