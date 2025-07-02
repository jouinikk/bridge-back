package com.example.cars.restcontrollers;

import com.example.cars.dto.SeanceDTO;
import com.example.cars.entities.Seance;
import com.example.cars.entities.LigneEau;
import com.example.cars.mappers.SeanceMapper;
import com.example.cars.services.SeanceService;
import com.example.cars.services.ILigneEauService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seances")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200/")
public class SeanceController {
    
    private final SeanceService seanceService;
    private final SeanceMapper seanceMapper;
    private final ILigneEauService ligneEauService;
    
    @PostMapping
    public ResponseEntity<?> createSeance(@RequestBody Seance seance) {
        try {
            // Validate required associations
            if (seance.getLigneEau() == null || seance.getLigneEau().getId() == null) {
                return ResponseEntity.badRequest()
                    .body("Une ligne d'eau est requise");
            }

            // Fetch complete LigneEau information
            LigneEau completeLigneEau = ligneEauService.getLigneEauById(seance.getLigneEau().getId());
            if (completeLigneEau == null) {
                return ResponseEntity.badRequest()
                    .body("La ligne d'eau spécifiée n'existe pas");
            }
            
            if (completeLigneEau.getPiscine() == null) {
                return ResponseEntity.badRequest()
                    .body("La piscine associée à la ligne d'eau est requise");
            }

            // Set the complete LigneEau and Piscine information
            seance.setLigneEau(completeLigneEau);
            seance.setPiscine(completeLigneEau.getPiscine());

            // Debug logging
            System.out.println("Creating seance with details:");
            System.out.println("LigneEau ID: " + seance.getLigneEau().getId());
            System.out.println("LigneEau Piscine: " + seance.getLigneEau().getPiscine());
            System.out.println("Direct Piscine: " + seance.getPiscine());

            // Check coach availability (pass null as excludeId for new seance)
            boolean isCoachAvailable = seanceService.isCoachAvailable(
                seance.getCoach().getId(), 
                seance.getDateDebut(), 
                seance.getDateFin(),
                null
            );
            
            // Check for conflicts (pass null as excludeId for new seance)
            boolean hasConflicts = seanceService.hasConflictingSeances(
                seance.getLigneEau().getId(),
                seance.getDateDebut(),
                seance.getDateFin(),
                null
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

            Seance savedSeance = seanceService.addSeance(seance);
            return ResponseEntity.ok(seanceMapper.toDTO(savedSeance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Une erreur est survenue lors de la création de la séance: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<SeanceDTO>> getAllSeances() {
        List<Seance> seances = seanceService.getAllSeances();
        return ResponseEntity.ok(seanceMapper.toDTOList(seances));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SeanceDTO> getSeanceById(@PathVariable Long id) {
        Seance seance = seanceService.getSeanceById(id);
        return seance != null ? 
            ResponseEntity.ok(seanceMapper.toDTO(seance)) : 
            ResponseEntity.notFound().build();
    }
    
    @GetMapping("/coach/{coachId}")
    public ResponseEntity<List<SeanceDTO>> getSeancesByCoach(@PathVariable Long coachId) {
        List<Seance> seances = seanceService.getSeancesByCoachId(coachId);
        return ResponseEntity.ok(seanceMapper.toDTOList(seances));
    }
    
    @GetMapping("/groupe/{groupeId}")
    public ResponseEntity<List<SeanceDTO>> getSeancesByGroupe(@PathVariable Long groupeId) {
        List<Seance> seances = seanceService.getSeancesByGroupeId(groupeId);
        return ResponseEntity.ok(seanceMapper.toDTOList(seances));
    }
    
    @GetMapping("/ligne-eau/{ligneEauId}")
    public ResponseEntity<List<SeanceDTO>> getSeancesByLigneEau(@PathVariable Long ligneEauId) {
        List<Seance> seances = seanceService.getSeancesByLigneEauId(ligneEauId);
        return ResponseEntity.ok(seanceMapper.toDTOList(seances));
    }
    
    @GetMapping("/coach/{coachId}/date-range")
    public ResponseEntity<List<SeanceDTO>> getSeancesByCoachAndDateRange(
            @PathVariable Long coachId,
            @RequestParam ZonedDateTime debut,
            @RequestParam ZonedDateTime fin) {
        List<Seance> seances = seanceService.getSeancesByCoachAndDateRange(coachId, debut, fin);
        return ResponseEntity.ok(seanceMapper.toDTOList(seances));
    }
    
    @GetMapping("/ligne-eau/{ligneEauId}/date-range")
    public ResponseEntity<List<SeanceDTO>> getSeancesByLigneEauAndDateRange(
            @PathVariable Long ligneEauId,
            @RequestParam ZonedDateTime debut,
            @RequestParam ZonedDateTime fin) {
        List<Seance> seances = seanceService.getSeancesByLigneEauAndDateRange(ligneEauId, debut, fin);
        return ResponseEntity.ok(seanceMapper.toDTOList(seances));
    }
      @PutMapping("/{id}")
    public ResponseEntity<?> updateSeance(@PathVariable Long id, @RequestBody SeanceDTO seanceDto) {
        try {
            if (!id.equals(seanceDto.getId())) {
                return ResponseEntity.badRequest()
                    .body("L'ID de la séance ne correspond pas");
            }
            
            // Convert DTO to entity
            Seance seance = seanceMapper.toEntity(seanceDto);
            
            // Validate required associations
            if (seance.getLigneEau() == null) {
                return ResponseEntity.badRequest()
                    .body("Une ligne d'eau est requise");
            }
            
            if (seance.getLigneEau().getPiscine() == null) {
                return ResponseEntity.badRequest()
                    .body("La piscine associée à la ligne d'eau est requise");
            }

            // Set the piscine from ligneEau to ensure consistency
            seance.setPiscine(seance.getLigneEau().getPiscine());
              // Check required relationships
            if (seance.getCoach() == null) {
                return ResponseEntity.badRequest()
                    .body("Un coach est requis");
            }
            
            if (seance.getGroupe() == null) {
                return ResponseEntity.badRequest()
                    .body("Un groupe est requis");
            }

            // Check coach availability (pass the seance id to exclude it from conflict check)
            boolean isCoachAvailable = seanceService.isCoachAvailable(
                seance.getCoach().getId(), 
                seance.getDateDebut(), 
                seance.getDateFin(),
                seance.getId()
            );
            
            // Check for conflicts (pass the seance id to exclude it from conflict check)
            boolean hasConflicts = seanceService.hasConflictingSeances(
                seance.getLigneEau().getId(),
                seance.getDateDebut(),
                seance.getDateFin(),
                seance.getId()
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
            
            Seance updatedSeance = seanceService.updateSeance(seance);
            return ResponseEntity.ok(seanceMapper.toDTO(updatedSeance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Une erreur est survenue lors de la mise à jour de la séance: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable Long id) {
        seanceService.deleteSeance(id);
        return ResponseEntity.ok().build();
    }
}