package com.example.cars.restcontrollers;

import com.example.cars.entities.LigneEau;
import com.example.cars.services.ILigneEauService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lignes-eau")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LigneEauController {
    
    private final ILigneEauService ligneEauService;

    @GetMapping
    public ResponseEntity<List<LigneEau>> getAllLignesEau() {
        return ResponseEntity.ok(ligneEauService.getAllLignesEau());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneEau> getLigneEauById(@PathVariable Long id) {
        return ResponseEntity.ok(ligneEauService.getLigneEauById(id));
    }

    @GetMapping("/piscine/{piscineId}")
    public ResponseEntity<List<LigneEau>> getLignesEauByPiscineId(@PathVariable Long piscineId) {
        return ResponseEntity.ok(ligneEauService.getLignesEauByPiscineId(piscineId));
    }

    @GetMapping("/piscine/{piscineId}/numero/{numero}")
    public ResponseEntity<LigneEau> getLigneEauByPiscineAndNumero(
            @PathVariable Long piscineId,
            @PathVariable String numero) {
        return ResponseEntity.ok(ligneEauService.getLigneEauByPiscineAndNumero(piscineId, numero));
    }

    @GetMapping("/available")
    public ResponseEntity<List<LigneEau>> getAvailableLignesEau(
            @RequestParam String dateDebut,
            @RequestParam String dateFin) {
        try {
            // Parse ISO 8601 format with timezone (e.g., "2025-06-23T08:00:00.000+01:00")
            ZonedDateTime startZonedDateTime = ZonedDateTime.parse(dateDebut);
            ZonedDateTime endZonedDateTime = ZonedDateTime.parse(dateFin);
            
            // Convert to Europe/Paris timezone to match database storage
            LocalDateTime startDateTime = startZonedDateTime.withZoneSameInstant(java.time.ZoneId.of("Europe/Paris")).toLocalDateTime();
            LocalDateTime endDateTime = endZonedDateTime.withZoneSameInstant(java.time.ZoneId.of("Europe/Paris")).toLocalDateTime();
            
            return ResponseEntity.ok(ligneEauService.getAvailableLignesEau(startDateTime, endDateTime));
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error parsing dates: " + e.getMessage());
            System.err.println("dateDebut: " + dateDebut);
            System.err.println("dateFin: " + dateFin);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/piscine/{piscineId}/available")
    public ResponseEntity<List<LigneEau>> getAvailableLignesEauByPiscine(
            @PathVariable Long piscineId,
            @RequestParam String dateDebut,
            @RequestParam String dateFin) {
        try {
            // Parse ISO 8601 format with timezone (e.g., "2025-06-23T08:00:00.000+01:00")
            ZonedDateTime startZonedDateTime = ZonedDateTime.parse(dateDebut);
            ZonedDateTime endZonedDateTime = ZonedDateTime.parse(dateFin);
            
            // Convert to Europe/Paris timezone to match database storage
            LocalDateTime startDateTime = startZonedDateTime.withZoneSameInstant(java.time.ZoneId.of("Europe/Paris")).toLocalDateTime();
            LocalDateTime endDateTime = endZonedDateTime.withZoneSameInstant(java.time.ZoneId.of("Europe/Paris")).toLocalDateTime();
            
            return ResponseEntity.ok(ligneEauService.getAvailableLignesEauByPiscine(piscineId, startDateTime, endDateTime));
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error parsing dates: " + e.getMessage());
            System.err.println("dateDebut: " + dateDebut);
            System.err.println("dateFin: " + dateFin);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<LigneEau> addLigneEau(@RequestBody LigneEau ligneEau) {
        return ResponseEntity.ok(ligneEauService.addLigneEau(ligneEau));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneEau> updateLigneEau(@PathVariable Long id, @RequestBody LigneEau ligneEau) {
        ligneEau.setId(id);
        return ResponseEntity.ok(ligneEauService.updateLigneEau(ligneEau));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneEau(@PathVariable Long id) {
        ligneEauService.deleteLigneEau(id);
        return ResponseEntity.ok().build();
    }
}