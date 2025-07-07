package com.example.cars.services;

import com.example.cars.entities.Seance;

import java.time.ZonedDateTime;
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
    
    List<Seance> getSeancesByCoachAndDateRange(Long coachId, ZonedDateTime debut, ZonedDateTime fin);
    
    List<Seance> getSeancesByLigneEauAndDateRange(Long ligneEauId, ZonedDateTime debut, ZonedDateTime fin);
    
    boolean hasConflictingSeances(Long ligneEauId, ZonedDateTime dateDebut, ZonedDateTime dateFin, Long excludeId);
    
    boolean isCoachAvailable(Long coachId, ZonedDateTime dateDebut, ZonedDateTime dateFin, Long excludeId);
    
    boolean isWithinPoolHours(Long ligneEauId, ZonedDateTime dateDebut, ZonedDateTime dateFin);
}