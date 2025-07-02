package com.example.cars.restcontrollers;

import com.example.cars.dto.GroupNameDTO;
import com.example.cars.entities.Groupe;
import com.example.cars.services.IGroupeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groupes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class GroupeController {
    
    private final IGroupeService groupeService;

    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroupes() {
        return ResponseEntity.ok(groupeService.getAllGroupes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Groupe> getGroupeById(@PathVariable Long id) {
        return ResponseEntity.ok(groupeService.getGroupeById(id));
    }

    @GetMapping("/coach/{coachId}")
    public ResponseEntity<List<Groupe>> getGroupesByCoachId(@PathVariable Long coachId) {
        return ResponseEntity.ok(groupeService.getGroupesByCoachId(coachId));
    }

    @PostMapping
    public ResponseEntity<Groupe> addGroupe(@RequestBody Groupe groupe) {
        return ResponseEntity.ok(groupeService.addGroupe(groupe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Groupe> updateGroupe(@PathVariable Long id, @RequestBody Groupe groupe) {
        groupe.setId(id);
        return ResponseEntity.ok(groupeService.updateGroupe(groupe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable Long id) {
        groupeService.deleteGroupe(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{groupeId}/nageur/{nageurId}")
    public ResponseEntity<Void> addNageurToGroupe(@PathVariable Long groupeId, @PathVariable Long nageurId) {
        groupeService.addNageurToGroupe(groupeId, nageurId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupeId}/nageur/{nageurId}")
    public ResponseEntity<Void> removeNageurFromGroupe(@PathVariable Long groupeId, @PathVariable Long nageurId) {
        groupeService.removeNageurFromGroupe(groupeId, nageurId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/names")
    public List<GroupNameDTO> getAllGroupeNames() {
        List<GroupNameDTO> groupes = groupeService.getGroupeNames();
        System.out.println("Groups: " + groupes);
        return groupes;
    }
}