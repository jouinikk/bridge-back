package com.example.cars.restcontrollers;

import com.example.cars.entities.SeanceBienEtre;
import com.example.cars.services.SeanceBienEtreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seancesbienetre")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SeanceBienEtreController {

    private final SeanceBienEtreService seanceService;

    @PostMapping
    public SeanceBienEtre addSeance(@RequestBody SeanceBienEtre seance) {
        return seanceService.addSeance(seance);
    }

    @GetMapping
    public List<SeanceBienEtre> getAllSeances() {
        return seanceService.getAllSeances();
    }

    @GetMapping("/{id}")
    public SeanceBienEtre getSeanceById(@PathVariable Long id) {
        return seanceService.getSeanceById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSeance(@PathVariable Long id) {
        seanceService.deleteSeance(id);
    }
}
