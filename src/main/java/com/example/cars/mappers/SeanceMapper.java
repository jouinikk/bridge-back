package com.example.cars.mappers;

import com.example.cars.dto.SeanceDTO;
import com.example.cars.entities.Seance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeanceMapper {

    /**
     * Convert a Seance entity to a SeanceDTO
     */
    public SeanceDTO toDTO(Seance seance) {
        if (seance == null) {
            return null;
        }
        
        return SeanceDTO.builder()
                .id(seance.getId())
                .titre(seance.getTitre())
                .description(seance.getDescription())
                .dateDebut(seance.getDateDebut())
                .dateFin(seance.getDateFin())
                .objectifs(seance.getObjectifs())
                .typeEntrainement(seance.getTypeEntrainement())
                
                // Coach information
                .coachId(seance.getCoach() != null ? seance.getCoach().getId() : null)
                .coachNom(seance.getCoach() != null ? seance.getCoach().getNom() : null)
                .coachPrenom(seance.getCoach() != null ? seance.getCoach().getPrenom() : null)
                
                // Groupe information
                .groupeId(seance.getGroupe() != null ? seance.getGroupe().getId() : null)
                .groupeNom(seance.getGroupe() != null ? seance.getGroupe().getNom() : null)
                
                // LigneEau information
                .ligneEauId(seance.getLigneEau() != null ? seance.getLigneEau().getId() : null)
                .ligneEauNumero(seance.getLigneEau() != null ? seance.getLigneEau().getNumero() : null)
                
                // Piscine information
                .piscineId(seance.getPiscine() != null ? seance.getPiscine().getId() : null)
                .piscineNom(seance.getPiscine() != null ? seance.getPiscine().getNom() : null)
                
                .statut(seance.getStatut())
                .nombreMaxParticipants(seance.getNombreMaxParticipants())
                .niveau(seance.getNiveau())
                .estDisponible(seance.isEstDisponible())
                .build();
    }
    
    /**
     * Convert a list of Seance entities to a list of SeanceDTO objects
     */
    public List<SeanceDTO> toDTOList(List<Seance> seances) {
        if (seances == null) {
            return null;
        }
        return seances.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}