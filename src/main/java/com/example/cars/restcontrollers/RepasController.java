package com.example.cars.restcontrollers;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.cars.entities.Repas;
import com.example.cars.services.RepasService;

import java.util.List;

@RestController
@RequestMapping("/api/repas")
@RequiredArgsConstructor
public class RepasController {

    private final RepasService repasService;

    @PostMapping
    public Repas add(@RequestBody Repas repas) {
        return repasService.addRepas(repas);
    }

    @GetMapping
    public List<Repas> getAll() {
        return repasService.getAllRepas();
    }

    @GetMapping("/{id}")
    public Repas getById(@PathVariable Long id) {
        return repasService.getRepasById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repasService.deleteRepas(id);
    }
}
