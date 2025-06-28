package com.example.cars.mappers;

import com.example.cars.dto.SeanceDTO;
import com.example.cars.entities.Seance;
import com.example.cars.entities.LigneEau;
import com.example.cars.services.ILigneEauService;
import com.example.cars.services.ICoachService;
import com.example.cars.services.IGroupeService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeanceMapper {

    private final ILigneEauService ligneEauService;
    private final ICoachService coachService;
    private final IGroupeService groupeService;

    public SeanceMapper(ILigneEauService ligneEauService, ICoachService coachService, 
                       IGroupeService groupeService) {
        this.ligneEauService = ligneEauService;
        this.coachService = coachService;
        this.groupeService = groupeService;
    }

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

    /**
     * Convert a SeanceDTO to a Seance entity
     */
    public Seance toEntity(SeanceDTO dto) {
        if (dto == null) {
            return null;
        }

        Seance seance = new Seance();
        seance.setId(dto.getId());
        seance.setTitre(dto.getTitre());
        seance.setDescription(dto.getDescription());
        seance.setDateDebut(dto.getDateDebut());
        seance.setDateFin(dto.getDateFin());
        seance.setObjectifs(dto.getObjectifs());
        seance.setTypeEntrainement(dto.getTypeEntrainement());
        seance.setNombreMaxParticipants(dto.getNombreMaxParticipants());
        seance.setNiveau(dto.getNiveau());
        seance.setEstDisponible(dto.isEstDisponible());
        seance.setStatut(dto.getStatut());

        // Set the LigneEau with complete information
        if (dto.getLigneEauId() != null) {
            LigneEau ligneEau = ligneEauService.getLigneEauById(dto.getLigneEauId());
            seance.setLigneEau(ligneEau);
            if (ligneEau != null) {
                seance.setPiscine(ligneEau.getPiscine());
            }
        }

        // Set the Coach
        if (dto.getCoachId() != null) {
            seance.setCoach(coachService.getCoachById(dto.getCoachId()));
        }

        // Set the Groupe
        if (dto.getGroupeId() != null) {
            seance.setGroupe(groupeService.getGroupeById(dto.getGroupeId()));
        }

        return seance;
    }
}