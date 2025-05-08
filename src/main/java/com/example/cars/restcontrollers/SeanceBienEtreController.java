package com.example.cars.restcontrollers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.cars.entities.SeanceBienEtre;
import com.example.cars.services.SeanceBienEtreService;

import java.util.List;

@RestController
@RequestMapping("/api/seances")
@RequiredArgsConstructor
public class SeanceBienEtreController {

    private final SeanceBienEtreService seanceService;

    @PostMapping
    public SeanceBienEtre add(@RequestBody SeanceBienEtre seance) {
        return seanceService.addSeance(seance);
    }

    @GetMapping
    public List<SeanceBienEtre> getAll() {
        return seanceService.getAllSeances();
    }

    @GetMapping("/{id}")
    public SeanceBienEtre getById(@PathVariable Long id) {
        return seanceService.getSeanceById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        seanceService.deleteSeance(id);
    }
}
