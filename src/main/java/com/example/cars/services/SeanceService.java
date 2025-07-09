package com.example.cars.services;

import com.example.cars.entities.Seance;
import com.example.cars.Repositories.SeanceRepository;
import com.example.cars.Repositories.DisponibiliteRepository;
import com.example.cars.entities.Disponibilite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeanceService implements ISeanceService {
    
    private final SeanceRepository seanceRepository;
    private final DisponibiliteRepository disponibiliteRepository;
    
    @Override
    public Seance addSeance(Seance seance) {
        // Debug logging
        System.out.println("Adding seance with details:");
        System.out.println("LigneEau ID: " + seance.getLigneEau().getId());
        System.out.println("LigneEau Piscine: " + seance.getLigneEau().getPiscine());
        System.out.println("Direct Piscine: " + seance.getPiscine());

        // Validate that LigneEau exists and belongs to a Piscine
        if (seance.getLigneEau() == null || seance.getLigneEau().getPiscine() == null) {
            throw new IllegalArgumentException("La ligne d'eau et sa piscine associée sont requises");
        }

        // Ensure the piscine reference is consistent
        seance.setPiscine(seance.getLigneEau().getPiscine());
        
        // Validate session times
        ZonedDateTime dateDebut = seance.getDateDebut();
        ZonedDateTime dateFin = seance.getDateFin();
        
        // Validate pool hours
        if (!isWithinPoolHours(seance.getLigneEau().getId(), dateDebut, dateFin)) {
            System.out.println("Session time validation failed:");
            System.out.println("Date début: " + dateDebut);
            System.out.println("Date fin: " + dateFin);
            System.out.println("Day of week: " + dateDebut.getDayOfWeek());
            System.out.println("Time début: " + dateDebut.toLocalTime());
            System.out.println("Time fin: " + dateFin.toLocalTime());
            throw new IllegalArgumentException("La séance est en dehors des heures d'ouverture de la piscine");
        }

        // Save and return the seance
        return seanceRepository.save(seance);
    }
    
    @Override
    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }
    
    @Override
    public Seance getSeanceById(Long id) {
        return seanceRepository.findById(id).orElse(null);
    }
    
    @Override
    public Seance updateSeance(Seance seance) {
        // Ensure the piscine reference is consistent
        seance.setPiscine(seance.getLigneEau().getPiscine());
        
        // Validate session times
        ZonedDateTime dateDebut = seance.getDateDebut();
        ZonedDateTime dateFin = seance.getDateFin();
        
        // Validate pool hours
        if (!isWithinPoolHours(seance.getLigneEau().getId(), dateDebut, dateFin)) {
            System.out.println("Session time validation failed:");
            System.out.println("Date début: " + dateDebut);
            System.out.println("Date fin: " + dateFin);
            System.out.println("Day of week: " + dateDebut.getDayOfWeek());
            System.out.println("Time début: " + dateDebut.toLocalTime());
            System.out.println("Time fin: " + dateFin.toLocalTime());
            throw new IllegalArgumentException("La séance est en dehors des heures d'ouverture de la piscine");
        }
        
        return seanceRepository.save(seance);
    }
    
    @Override
    public void deleteSeance(Long id) {
        seanceRepository.deleteById(id);
    }
    
    @Override
    public List<Seance> getSeancesByCoachId(Long coachId) {
        return seanceRepository.findByCoachId(coachId);
    }
    
    @Override
    public List<Seance> getSeancesByGroupeId(Long groupeId) {
        return seanceRepository.findByGroupeId(groupeId);
    }
    
    @Override
    public List<Seance> getSeancesByLigneEauId(Long ligneEauId) {
        return seanceRepository.findByLigneEauId(ligneEauId);
    }
    
    @Override
    public List<Seance> getSeancesByCoachAndDateRange(Long coachId, ZonedDateTime debut, ZonedDateTime fin) {
        return seanceRepository.findByCoachIdAndDateDebutBetween(coachId, debut, fin);
    }
    
    @Override
    public List<Seance> getSeancesByLigneEauAndDateRange(Long ligneEauId, ZonedDateTime debut, ZonedDateTime fin) {
        return seanceRepository.findByLigneEauIdAndDateDebutBetween(ligneEauId, debut, fin);
    }

    @Override
    public boolean hasConflictingSeances(Long ligneEauId, ZonedDateTime dateDebut, ZonedDateTime dateFin, Long excludeId) {
        List<Seance> conflictingSeances = seanceRepository.findConflictingSessionsForLigneEau(
            ligneEauId, 
            dateDebut, 
            dateFin,
            excludeId
        );
        return !conflictingSeances.isEmpty();
    }
    
    @Override
    public boolean isCoachAvailable(Long coachId, ZonedDateTime dateDebut, ZonedDateTime dateFin, Long excludeId) {
        List<Seance> conflictingSeances = seanceRepository.findConflictingSessionsForCoach(
            coachId, 
            dateDebut, 
            dateFin,
            excludeId
        );
        return conflictingSeances.isEmpty();
    }

    @Override
    public boolean isWithinPoolHours(Long ligneEauId, ZonedDateTime dateDebut, ZonedDateTime dateFin) {
        // Get the day of week for the seance
        DayOfWeek jourSemaine = dateDebut.getDayOfWeek();
        
        System.out.println("=== POOL HOURS VALIDATION ===");
        System.out.println("Ligne Eau ID: " + ligneEauId);
        System.out.println("Day of week: " + jourSemaine);
        System.out.println("Original datetime: " + dateDebut + " to " + dateFin);
        
        // Find disponibilite for this ligne d'eau and day
        List<Disponibilite> disponibilites = disponibiliteRepository.findByLigneEauIdAndJourSemaine(ligneEauId, jourSemaine);
        
        System.out.println("Found " + disponibilites.size() + " disponibilites for this day and ligne eau");
        
        if (disponibilites.isEmpty()) {
            System.out.println("No disponibilite found - returning false");
            return false; // No disponibilite found for this day
        }

        Disponibilite dispo = disponibilites.get(0);
        
        // Convert back to the original frontend timezone (GMT+1) to get the actual user time
        // The server automatically converts to Europe/Paris (GMT+2), so we need to convert back
        java.time.ZoneId frontendTimeZone = java.time.ZoneId.of("GMT+1");
        LocalTime seanceDebut = dateDebut.withZoneSameInstant(frontendTimeZone).toLocalTime();
        LocalTime seanceFin = dateFin.withZoneSameInstant(frontendTimeZone).toLocalTime();
        
        // Debug logging
        System.out.println("Pool hours: " + dispo.getHeureOuverture() + " - " + dispo.getHeureFermeture());
        System.out.println("Session times (direct from payload): " + seanceDebut + " - " + seanceFin);
        System.out.println("Lane available (estDisponible): " + dispo.isEstDisponible());
        
        // Individual checks
        boolean laneAvailable = dispo.isEstDisponible();
        boolean startTimeOk = !seanceDebut.isBefore(dispo.getHeureOuverture());
        boolean endTimeOk = !seanceFin.isAfter(dispo.getHeureFermeture());
        
        System.out.println("Lane available: " + laneAvailable);
        System.out.println("Start time check (" + seanceDebut + " not before " + dispo.getHeureOuverture() + "): " + startTimeOk);
        System.out.println("End time check (" + seanceFin + " not after " + dispo.getHeureFermeture() + "): " + endTimeOk);
        
        // Check if the seance is within pool hours and the lane is available
        boolean isWithinHours = laneAvailable && startTimeOk && endTimeOk;
        
        System.out.println("Final result: " + isWithinHours);
        System.out.println("=== END POOL HOURS VALIDATION ===");
        
        return isWithinHours;
    }
}