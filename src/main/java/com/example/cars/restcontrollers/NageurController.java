package com.example.cars.restcontrollers;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.cars.entities.Nageur;
import com.example.cars.services.NageurService;

import java.util.List;

@RestController
@RequestMapping("/api/nageurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class NageurController {

    private final NageurService nageurService;

    @PostMapping
    public Nageur add(@RequestBody Nageur nageur) {
        return nageurService.addNageur(nageur);
    }

    @GetMapping
    public List<Nageur> getAll() {
        return nageurService.getAllNageurs();
    }

    @GetMapping("/{id}")
    public Nageur getById(@PathVariable Long id) {
        return nageurService.getNageurById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        nageurService.deleteNageur(id);
    }
}
