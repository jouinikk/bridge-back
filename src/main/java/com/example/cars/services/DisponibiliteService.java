package com.example.cars.services;

import com.example.cars.entities.Disponibilite;
import com.example.cars.Repositories.DisponibiliteRepository;
import com.example.cars.Repositories.LigneEauRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisponibiliteService implements IDisponibiliteService {
    
    private final DisponibiliteRepository disponibiliteRepository;
    private final LigneEauRepository ligneEauRepository;
    
    @Override
    public Disponibilite addDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }
    
    @Override
    public List<Disponibilite> getAllDisponibilites() {
        return disponibiliteRepository.findAll();
    }
    
    @Override
    public Disponibilite getDisponibiliteById(Long id) {
        return disponibiliteRepository.findById(id).orElse(null);
    }
    
    @Override
    public Disponibilite updateDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }
    
    @Override
    public void deleteDisponibilite(Long id) {
        disponibiliteRepository.deleteById(id);
    }
    
    @Override
    public List<Disponibilite> getDisponibilitesByPiscineId(Long piscineId) {
        return disponibiliteRepository.findByPiscineId(piscineId);
    }
    
    @Override
    public List<Disponibilite> getDisponibilitesByLigneEauId(Long ligneEauId) {
        return disponibiliteRepository.findByLigneEauId(ligneEauId);
    }
    
    @Override
    public List<Disponibilite> getDisponibilitesByJourSemaine(DayOfWeek jourSemaine) {
        return disponibiliteRepository.findByJourSemaine(jourSemaine);
    }
    
    @Override
    public List<Disponibilite> getDisponibilitesByPiscineAndJour(Long piscineId, DayOfWeek jourSemaine) {
        return disponibiliteRepository.findByPiscineIdAndJourSemaine(piscineId, jourSemaine);
    }
    
    @Override
    public List<Disponibilite> getDisponibilitesByLigneEauAndJour(Long ligneEauId, DayOfWeek jourSemaine) {
        return disponibiliteRepository.findByLigneEauIdAndJourSemaine(ligneEauId, jourSemaine);
    }
    
    @Override
    public boolean isLigneEauDisponible(Long ligneEauId, LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();
        
        List<Disponibilite> disponibilites = disponibiliteRepository
                .findByLigneEauIdAndJourSemaine(ligneEauId, dayOfWeek);
        
        if (disponibilites.isEmpty()) {
            return false; // No availability record found
        }
        
        // Check if any availability record covers the requested time
        return disponibilites.stream().anyMatch(disp -> 
            disp.isEstDisponible() && // Lane must be marked as available
            disp.getHeureOuverture() != null && 
            disp.getHeureFermeture() != null &&
            !time.isBefore(disp.getHeureOuverture()) && // Time must be after opening
            !time.isAfter(disp.getHeureFermeture()) // Time must be before closing
        );
    }
    
    @Override
    public List<Disponibilite> createMultipleDisponibilites(List<Disponibilite> disponibilites) {
        // Validate that all ligne_eau_id references exist before saving
        List<Disponibilite> validDisponibilites = disponibilites.stream()
            .filter(disp -> {
                if (disp.getLigneEau() != null && disp.getLigneEau().getId() != null) {
                    // Check if ligne_eau exists
                    return ligneEauRepository.existsById(disp.getLigneEau().getId());
                }
                return false; // Skip if ligne_eau is null or id is null
            })
            .collect(Collectors.toList());
            
        if (validDisponibilites.size() != disponibilites.size()) {
            System.err.println("Warning: Some disponibilites were skipped due to invalid ligne_eau references");
        }
        
        return disponibiliteRepository.saveAll(validDisponibilites);
    }
}