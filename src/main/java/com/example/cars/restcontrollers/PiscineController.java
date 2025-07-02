// package com.example.cars.restcontrollers;

// import com.example.cars.entities.Piscine;
// import com.example.cars.services.IPiscineService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/v1/piscines")
// @RequiredArgsConstructor
// @CrossOrigin("*")
// public class PiscineController {
    
//     private final IPiscineService piscineService;

//     @GetMapping
//     public ResponseEntity<List<Piscine>> getAllPiscines() {
//         return ResponseEntity.ok(piscineService.getAllPiscines());
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Piscine> getPiscineById(@PathVariable Long id) {
//         return ResponseEntity.ok(piscineService.getPiscineById(id));
//     }

//     @GetMapping("/ville/{ville}")
//     public ResponseEntity<List<Piscine>> getPiscinesByVille(@PathVariable String ville) {
//         return ResponseEntity.ok(piscineService.getPiscinesByVille(ville));
//     }

//     @GetMapping("/search/{keyword}")
//     public ResponseEntity<List<Piscine>> searchPiscines(@PathVariable String keyword) {
//         return ResponseEntity.ok(piscineService.searchPiscines(keyword));
//     }

//     @PostMapping
//     public ResponseEntity<Piscine> addPiscine(@RequestBody Piscine piscine) {
//         return ResponseEntity.ok(piscineService.addPiscine(piscine));
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Piscine> updatePiscine(@PathVariable Long id, @RequestBody Piscine piscine) {
//         piscine.setId(id);
//         return ResponseEntity.ok(piscineService.updatePiscine(piscine));
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deletePiscine(@PathVariable Long id) {
//         piscineService.deletePiscine(id);
//         return ResponseEntity.ok().build();
//     }
// }



package com.example.cars.restcontrollers;

import com.example.cars.entities.Piscine;
import com.example.cars.services.IPiscineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/piscines")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PiscineController {
    
    private final IPiscineService piscineService;

    @PostMapping("/import-excel")
    public ResponseEntity<Void> importPiscinesFromExcel(@RequestParam("file") MultipartFile file) {
        piscineService.importPiscinesFromExcel(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Piscine>> getAllPiscines() {
        return ResponseEntity.ok(piscineService.getAllPiscines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Piscine> getPiscineById(@PathVariable Long id) {
        return ResponseEntity.ok(piscineService.getPiscineById(id));
    }

    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<Piscine>> getPiscinesByVille(@PathVariable String ville) {
        return ResponseEntity.ok(piscineService.getPiscinesByVille(ville));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Piscine>> searchPiscines(@PathVariable String keyword) {
        return ResponseEntity.ok(piscineService.searchPiscines(keyword));
    }

    @PostMapping
    public ResponseEntity<Piscine> addPiscine(@RequestBody Piscine piscine) {
        return ResponseEntity.ok(piscineService.addPiscine(piscine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Piscine> updatePiscine(@PathVariable Long id, @RequestBody Piscine piscine) {
        piscine.setId(id);
        return ResponseEntity.ok(piscineService.updatePiscine(piscine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePiscine(@PathVariable Long id) {
        piscineService.deletePiscine(id);
        return ResponseEntity.ok().build();
    }

    // ... rest of your existing endpoints ...
}