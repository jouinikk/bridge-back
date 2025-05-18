package com.example.cars.dto;

import com.example.cars.entities.Seance.StatutSeance;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for simplified Seance information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeanceDTO {
    private Long id;
    private String titre;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String objectifs;
    private String typeEntrainement;
    
    // Coach simplified info
    private Long coachId;
    private String coachNom;
    private String coachPrenom;
    
    // Groupe simplified info
    private Long groupeId;
    private String groupeNom;
    
    // LigneEau simplified info
    private Long ligneEauId;
    private String ligneEauNumero;
    
    // Piscine simplified info
    private Long piscineId;
    private String piscineNom;
    
    private StatutSeance statut;
    private Integer nombreMaxParticipants;
    private String niveau;
    private boolean estDisponible;
}