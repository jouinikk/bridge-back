package com.example.cars.services;

import com.example.cars.entities.LigneEau;
import com.example.cars.entities.Disponibilite;
import com.example.cars.entities.Seance;
import com.example.cars.Repositories.LigneEauRepository;
import com.example.cars.Repositories.DisponibiliteRepository;
import com.example.cars.Repositories.SeanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LigneEauService implements ILigneEauService {
    
    private final LigneEauRepository ligneEauRepository;
    private final DisponibiliteRepository disponibiliteRepository;
    private final SeanceRepository seanceRepository;
    
    @Override
    public LigneEau addLigneEau(LigneEau ligneEau) {
        return ligneEauRepository.save(ligneEau);
    }
    
    @Override
    public List<LigneEau> getAllLignesEau() {
        return ligneEauRepository.findAll();
    }
    
    @Override
    public LigneEau getLigneEauById(Long id) {
        return ligneEauRepository.findById(id).orElse(null);
    }
    
    @Override
    public LigneEau updateLigneEau(LigneEau ligneEau) {
        return ligneEauRepository.save(ligneEau);
    }
    
    @Override
    public void deleteLigneEau(Long id) {
        ligneEauRepository.deleteById(id);
    }
    
    @Override
    public List<LigneEau> getLignesEauByPiscineId(Long piscineId) {
        return ligneEauRepository.findByPiscineId(piscineId);
    }
    
    @Override
    public LigneEau getLigneEauByPiscineAndNumero(Long piscineId, String numero) {
        return ligneEauRepository.findByPiscineIdAndNumero(piscineId, numero);
    }
    
    @Override
    public List<LigneEau> getAvailableLignesEau(LocalDateTime dateDebut, LocalDateTime dateFin) {
        DayOfWeek dayOfWeek = dateDebut.getDayOfWeek();
        LocalTime timeDebut = dateDebut.toLocalTime();
        LocalTime timeFin = dateFin.toLocalTime();
        
        // Get all lignes eau
        List<LigneEau> allLignesEau = ligneEauRepository.findAll();
        
        return allLignesEau.stream()
                .filter(ligneEau -> isLigneEauAvailable(ligneEau, dayOfWeek, timeDebut, timeFin, dateDebut, dateFin))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<LigneEau> getAvailableLignesEauByPiscine(Long piscineId, LocalDateTime dateDebut, LocalDateTime dateFin) {
        DayOfWeek dayOfWeek = dateDebut.getDayOfWeek();
        LocalTime timeDebut = dateDebut.toLocalTime();
        LocalTime timeFin = dateFin.toLocalTime();
        
        // Get lignes eau for specific piscine
        List<LigneEau> lignesEauOfPiscine = ligneEauRepository.findByPiscineId(piscineId);
        
        return lignesEauOfPiscine.stream()
                .filter(ligneEau -> isLigneEauAvailable(ligneEau, dayOfWeek, timeDebut, timeFin, dateDebut, dateFin))
                .collect(Collectors.toList());
    }
    
    private boolean isLigneEauAvailable(LigneEau ligneEau, DayOfWeek dayOfWeek, 
                                       LocalTime timeDebut, LocalTime timeFin,
                                       LocalDateTime dateTimeDebut, LocalDateTime dateTimeFin) {
        // 1. Check disponibilité (schedule availability)
        List<Disponibilite> disponibilites = disponibiliteRepository
                .findByLigneEauIdAndJourSemaine(ligneEau.getId(), dayOfWeek);
        
        boolean isScheduleAvailable = false;
        if (!disponibilites.isEmpty()) {
            // Check if any availability record covers the requested time range
            isScheduleAvailable = disponibilites.stream().anyMatch(disp -> 
                disp.isEstDisponible() && // Lane must be marked as available
                disp.getHeureOuverture() != null && 
                disp.getHeureFermeture() != null &&
                !timeDebut.isBefore(disp.getHeureOuverture()) && // Session start must be after opening
                !timeFin.isAfter(disp.getHeureFermeture()) // Session end must be before closing
            );
        }
        
        if (!isScheduleAvailable) {
            return false; // Not available according to schedule
        }
        
        // 2. Check for conflicting sessions (seances)
        // Convert LocalDateTime to ZonedDateTime using Europe/Paris timezone to match database storage
        ZonedDateTime zonedDateTimeDebut = dateTimeDebut.atZone(java.time.ZoneId.of("Europe/Paris"));
        ZonedDateTime zonedDateTimeFin = dateTimeFin.atZone(java.time.ZoneId.of("Europe/Paris"));
        
        List<Seance> conflictingSeances = seanceRepository.findConflictingSessionsForLigneEau(
                ligneEau.getId(), zonedDateTimeDebut, zonedDateTimeFin, null);
        
        // Available only if no conflicting sessions
        return conflictingSeances.isEmpty();
    }
}