package com.example.cars.restcontrollers;

import com.example.cars.entities.Disponibilite;
import com.example.cars.services.IDisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/disponibilites")
public class DisponibiliteController {

    @Autowired
    private IDisponibiliteService disponibiliteService;

    // Basic CRUD operations
    @PostMapping
    public Disponibilite addDisponibilite(@RequestBody Disponibilite disponibilite) {
        return disponibiliteService.addDisponibilite(disponibilite);
    }

    @GetMapping
    public List<Disponibilite> getAllDisponibilites() {
        return disponibiliteService.getAllDisponibilites();
    }

    @GetMapping("/{id}")
    public Disponibilite getDisponibiliteById(@PathVariable Long id) {
        return disponibiliteService.getDisponibiliteById(id);
    }

    @PutMapping
    public Disponibilite updateDisponibilite(@RequestBody Disponibilite disponibilite) {
        return disponibiliteService.updateDisponibilite(disponibilite);
    }

    @DeleteMapping("/{id}")
    public void deleteDisponibilite(@PathVariable Long id) {
        disponibiliteService.deleteDisponibilite(id);
    }

    // Custom queries
    @GetMapping("/piscine/{piscineId}")
    public List<Disponibilite> getByPiscineId(@PathVariable Long piscineId) {
        return disponibiliteService.getDisponibilitesByPiscineId(piscineId);
    }

    @GetMapping("/ligne/{ligneEauId}")
    public List<Disponibilite> getByLigneEauId(@PathVariable Long ligneEauId) {
        return disponibiliteService.getDisponibilitesByLigneEauId(ligneEauId);
    }

    @GetMapping("/jour/{jourSemaine}")
    public List<Disponibilite> getByJourSemaine(@PathVariable DayOfWeek jourSemaine) {
        return disponibiliteService.getDisponibilitesByJourSemaine(jourSemaine);
    }

    @GetMapping("/piscine/{piscineId}/jour/{jourSemaine}")
    public List<Disponibilite> getByPiscineAndJour(
            @PathVariable Long piscineId,
            @PathVariable DayOfWeek jourSemaine) {
        return disponibiliteService.getDisponibilitesByPiscineAndJour(piscineId, jourSemaine);
    }

    @GetMapping("/ligne/{ligneEauId}/jour/{jourSemaine}")
    public List<Disponibilite> getByLigneEauAndJour(
            @PathVariable Long ligneEauId,
            @PathVariable DayOfWeek jourSemaine) {
        return disponibiliteService.getDisponibilitesByLigneEauAndJour(ligneEauId, jourSemaine);
    }

    @GetMapping("/ligne/{ligneEauId}/disponible")
    public boolean isLigneEauDisponible(
            @PathVariable Long ligneEauId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return disponibiliteService.isLigneEauDisponible(ligneEauId, dateTime);
    }
}