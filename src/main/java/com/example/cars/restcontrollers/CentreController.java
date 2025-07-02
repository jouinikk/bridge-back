package com.example.cars.restcontrollers;

import com.example.cars.entities.Centre;
import com.example.cars.services.ICentreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/centres")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CentreController {
    
    private final ICentreService centreService;

    @GetMapping
    public ResponseEntity<List<Centre>> getAllCentres() {
        return ResponseEntity.ok(centreService.getAllCentres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Centre> getCentreById(@PathVariable Long id) {
        return ResponseEntity.ok(centreService.getCentre(id));
    }

    @PostMapping
    public ResponseEntity<Centre> addCentre(@RequestBody Centre centre) {
        return ResponseEntity.ok(centreService.addCentre(centre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Centre> updateCentre(@PathVariable Long id, @RequestBody Centre centre) {
        centre.setId(id);
        return ResponseEntity.ok(centreService.updateCentre(centre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentre(@PathVariable Long id) {
        centreService.deleteCentre(id);
        return ResponseEntity.ok().build();
    }
}