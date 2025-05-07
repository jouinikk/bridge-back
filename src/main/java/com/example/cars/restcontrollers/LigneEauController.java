package com.example.cars.restcontrollers;

import com.example.cars.entities.LigneEau;
import com.example.cars.services.ILigneEauService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lignes-eau")
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