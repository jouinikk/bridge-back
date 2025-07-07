package com.example.cars.restcontrollers;

import com.example.cars.entities.Coach;
import com.example.cars.services.ICoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/coaches")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CoachController {
    
    private final ICoachService coachService;

    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches() {
        return ResponseEntity.ok(coachService.getAllCoaches());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Coach> getCoachById(@PathVariable Long id) {
        return ResponseEntity.ok(coachService.getCoachById(id));
    }

    @GetMapping("/specialite/{specialite}")
    public ResponseEntity<List<Coach>> getCoachesBySpecialite(@PathVariable String specialite) {
        return ResponseEntity.ok(coachService.getCoachesBySpecialite(specialite));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Coach>> getAvailableCoaches(
            @RequestParam String dateDebut,
            @RequestParam String dateFin) {
        try {
            // Parse ISO 8601 format with timezone (e.g., "2025-06-23T08:00:00.000+01:00")
            ZonedDateTime startDateTime = ZonedDateTime.parse(dateDebut);
            ZonedDateTime endDateTime = ZonedDateTime.parse(dateFin);
            
            // Convert ZonedDateTime to LocalDateTime using Europe/Paris timezone to match database storage
            LocalDateTime startLocalDateTime = startDateTime.withZoneSameInstant(java.time.ZoneId.of("Europe/Paris")).toLocalDateTime();
            LocalDateTime endLocalDateTime = endDateTime.withZoneSameInstant(java.time.ZoneId.of("Europe/Paris")).toLocalDateTime();
            
            return ResponseEntity.ok(coachService.getAvailableCoaches(startLocalDateTime, endLocalDateTime));
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error parsing dates: " + e.getMessage());
            System.err.println("dateDebut: " + dateDebut);
            System.err.println("dateFin: " + dateFin);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Coach> addCoach(@RequestBody Coach coach) {
        return ResponseEntity.ok(coachService.addCoach(coach));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coach> updateCoach(@PathVariable Long id, @RequestBody Coach coach) {
        coach.setId(id);
        return ResponseEntity.ok(coachService.updateCoach(coach));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        coachService.deleteCoach(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/groupes")
    public ResponseEntity<List<Coach>> getCoachGroupes(@PathVariable Long id) {
        return ResponseEntity.ok(coachService.getCoachWithGroupes(id));
    }
}