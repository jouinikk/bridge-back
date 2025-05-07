package com.example.cars.restcontrollers;

import com.example.cars.entities.Seance;
import com.example.cars.services.SeanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seances")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200/")
public class SeanceController {
    
    private final SeanceService seanceService;
    
    @PostMapping
    public ResponseEntity<?> createSeance(@RequestBody Seance seance) {
        try {
            // Validate required associations
            if (seance.getLigneEau() == null) {
                return ResponseEntity.badRequest()
                    .body("Une ligne d'eau est requise");
            }
            
            if (seance.getLigneEau().getPiscine() == null) {
                return ResponseEntity.badRequest()
                    .body("La piscine associée à la ligne d'eau est requise");
            }

            // Check coach availability
            boolean isCoachAvailable = seanceService.isCoachAvailable(
                seance.getCoach().getId(), 
                seance.getDateDebut(), 
                seance.getDateFin()
            );
            
            // Check for conflicts
            boolean hasConflicts = seanceService.hasConflictingSeances(
                seance.getLigneEau().getId(),
                seance.getDateDebut(),
                seance.getDateFin()
            );
            
            // Check pool hours
            boolean isWithinPoolHours = seanceService.isWithinPoolHours(
                seance.getLigneEau().getId(),
                seance.getDateDebut(),
                seance.getDateFin()
            );
            
            if (!isCoachAvailable) {
                return ResponseEntity.badRequest()
                    .body("Le coach n'est pas disponible pendant cette période");
            }
            
            if (hasConflicts) {
                return ResponseEntity.badRequest()
                    .body("La ligne d'eau est déjà réservée pendant cette période");
            }
            
            if (!isWithinPoolHours) {
                return ResponseEntity.badRequest()
                    .body("La séance est en dehors des heures d'ouverture de la piscine");
            }
            
            return ResponseEntity.ok(seanceService.addSeance(seance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Une erreur est survenue lors de la création de la séance");
        }
    }
    
    @GetMapping
    public List<Seance> getAllSeances() {
        return seanceService.getAllSeances();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Seance> getSeanceById(@PathVariable Long id) {
        Seance seance = seanceService.getSeanceById(id);
        return seance != null ? ResponseEntity.ok(seance) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/coach/{coachId}")
    public List<Seance> getSeancesByCoach(@PathVariable Long coachId) {
        return seanceService.getSeancesByCoachId(coachId);
    }
    
    @GetMapping("/groupe/{groupeId}")
    public List<Seance> getSeancesByGroupe(@PathVariable Long groupeId) {
        return seanceService.getSeancesByGroupeId(groupeId);
    }
    
    @GetMapping("/ligne-eau/{ligneEauId}")
    public List<Seance> getSeancesByLigneEau(@PathVariable Long ligneEauId) {
        return seanceService.getSeancesByLigneEauId(ligneEauId);
    }
    
    @GetMapping("/coach/{coachId}/date-range")
    public List<Seance> getSeancesByCoachAndDateRange(
            @PathVariable Long coachId,
            @RequestParam LocalDateTime debut,
            @RequestParam LocalDateTime fin) {
        return seanceService.getSeancesByCoachAndDateRange(coachId, debut, fin);
    }
    
    @GetMapping("/ligne-eau/{ligneEauId}/date-range")
    public List<Seance> getSeancesByLigneEauAndDateRange(
            @PathVariable Long ligneEauId,
            @RequestParam LocalDateTime debut,
            @RequestParam LocalDateTime fin) {
        return seanceService.getSeancesByLigneEauAndDateRange(ligneEauId, debut, fin);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeance(@PathVariable Long id, @RequestBody Seance seance) {
        try {
            if (!id.equals(seance.getId())) {
                return ResponseEntity.badRequest()
                    .body("L'ID de la séance ne correspond pas");
            }
            
            // Validate required associations
            if (seance.getLigneEau() == null) {
                return ResponseEntity.badRequest()
                    .body("Une ligne d'eau est requise");
            }
            
            if (seance.getLigneEau().getPiscine() == null) {
                return ResponseEntity.badRequest()
                    .body("La piscine associée à la ligne d'eau est requise");
            }
            
            // Check coach availability
            boolean isCoachAvailable = seanceService.isCoachAvailable(
                seance.getCoach().getId(), 
                seance.getDateDebut(), 
                seance.getDateFin()
            );
            
            // Check for conflicts
            boolean hasConflicts = seanceService.hasConflictingSeances(
                seance.getLigneEau().getId(),
                seance.getDateDebut(),
                seance.getDateFin()
            );
            
            if (!isCoachAvailable) {
                return ResponseEntity.badRequest()
                    .body("Le coach n'est pas disponible pendant cette période");
            }
            
            if (hasConflicts) {
                return ResponseEntity.badRequest()
                    .body("La ligne d'eau est déjà réservée pendant cette période");
            }
            
            return ResponseEntity.ok(seanceService.updateSeance(seance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Une erreur est survenue lors de la mise à jour de la séance");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable Long id) {
        seanceService.deleteSeance(id);
        return ResponseEntity.ok().build();
    }
}