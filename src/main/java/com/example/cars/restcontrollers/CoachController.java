package com.example.cars.restcontrollers;

import com.example.cars.entities.Coach;
import com.example.cars.services.ICoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@RequiredArgsConstructor
@CrossOrigin("*")
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