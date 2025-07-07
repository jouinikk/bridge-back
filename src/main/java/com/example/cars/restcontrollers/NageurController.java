package com.example.cars.restcontrollers;

import com.example.cars.entities.Nageur;
import com.example.cars.services.INageurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nageurs")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200/")
public class NageurController {
    
    private final INageurService nageurService;

    @GetMapping
    public ResponseEntity<List<Nageur>> getAllNageurs() {
        return ResponseEntity.ok(nageurService.getAllNageurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nageur> getNageurById(@PathVariable Long id) {
        return ResponseEntity.ok(nageurService.getNageurById(id));
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Nageur>> getNageursByNiveau(@PathVariable String niveau) {
        return ResponseEntity.ok(nageurService.getNageursByNiveau(niveau));
    }

    @GetMapping("/groupe/{groupeId}")
    public ResponseEntity<List<Nageur>> getNageursByGroupeId(@PathVariable Long groupeId) {
        return ResponseEntity.ok(nageurService.getNageursByGroupeId(groupeId));
    }

    @PostMapping
    public ResponseEntity<Nageur> addNageur(@RequestBody Nageur nageur) {
        return ResponseEntity.ok(nageurService.addNageur(nageur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nageur> updateNageur(@PathVariable Long id, @RequestBody Nageur nageur) {
        nageur.setId(id);
        return ResponseEntity.ok(nageurService.updateNageur(nageur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNageur(@PathVariable Long id) {
        nageurService.deleteNageur(id);
        return ResponseEntity.ok().build();
    }
}