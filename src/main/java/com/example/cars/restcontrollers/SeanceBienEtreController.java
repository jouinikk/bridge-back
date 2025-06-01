package com.example.cars.restcontrollers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.example.cars.entities.SeanceBienEtre;
import com.example.cars.services.SeanceBienEtreService;

import java.util.List;

@RestController
@RequestMapping("/api/seances")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Ajout CORS
public class SeanceBienEtreController {

    private final SeanceBienEtreService seanceService;

    @PostMapping
    public ResponseEntity<SeanceBienEtre> add(@RequestBody SeanceBienEtre seance) {
        try {
            SeanceBienEtre savedSeance = seanceService.addSeance(seance);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSeance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<SeanceBienEtre>> getAll() {
        try {
            List<SeanceBienEtre> seances = seanceService.getAllSeances();
            System.out.println("Séances récupérées: " + seances); // Log des données
            return ResponseEntity.ok(seances);
        } catch (Exception e) {
            e.printStackTrace(); // ← Ceci est crucial pour voir l'erreur réelle
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeanceBienEtre> getById(@PathVariable Long id) {
        try {
            SeanceBienEtre seance = seanceService.getSeanceById(id);
            if (seance != null) {
                return ResponseEntity.ok(seance);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            seanceService.deleteSeance(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}