package com.example.cars.services;

import com.example.cars.entities.Seance;

import java.time.LocalDateTime;
import java.util.List;

public interface ISeanceService {
    
    Seance addSeance(Seance seance);
    
    List<Seance> getAllSeances();
    
    Seance getSeanceById(Long id);
    
    Seance updateSeance(Seance seance);
    
    void deleteSeance(Long id);
    
    List<Seance> getSeancesByCoachId(Long coachId);
    
    List<Seance> getSeancesByGroupeId(Long groupeId);
    
    List<Seance> getSeancesByLigneEauId(Long ligneEauId);
    
    List<Seance> getSeancesByCoachAndDateRange(Long coachId, LocalDateTime debut, LocalDateTime fin);
    
    List<Seance> getSeancesByLigneEauAndDateRange(Long ligneEauId, LocalDateTime debut, LocalDateTime fin);
    
    boolean hasConflictingSeances(Long ligneEauId, LocalDateTime dateDebut, LocalDateTime dateFin);
    
    boolean isCoachAvailable(Long coachId, LocalDateTime dateDebut, LocalDateTime dateFin);
    
    boolean isWithinPoolHours(Long ligneEauId, LocalDateTime dateDebut, LocalDateTime dateFin);
}