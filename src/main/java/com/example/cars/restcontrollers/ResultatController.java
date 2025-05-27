package com.example.cars.restcontrollers;

import com.example.cars.entities.Resultat;
import com.example.cars.services.ResultatServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Api(value ="Result Controller", description = "")
@RestController
@RequestMapping("/Result")
public class ResultatController {

    private final ResultatServiceImpl resultatService;

    // Constructor injection
    public ResultatController(ResultatServiceImpl resultatService) {
        this.resultatService = resultatService;
    }

    @ApiOperation(value = "Add a new resultat", response = Resultat.class)
    @PostMapping
    public Resultat addResult(@RequestBody Resultat resultat) {
        // You might want to set creation timestamps here if relevant to your Resultat entity
        // resultat.setDateCreation(LocalDateTime.now());
        // resultat.setLastModified(LocalDateTime.now());
        return resultatService.addResultat(resultat);
    }

    @ApiOperation(value = "Get all resultat", response = List.class)
    @GetMapping
    public List<Resultat> getAllResults() {
        return resultatService.getAllResultat();
    }

    @ApiOperation(value = "Get a resultat by Id", response = Resultat.class)
    @GetMapping("/{id}")
    public Optional<Resultat> getResultById(@PathVariable Long id) {
        return resultatService.getResultatById(id);
    }


    @ApiOperation(value = "Update an existing resultat", response = Resultat.class)
    @PutMapping("/{id}")
    public Resultat updateResultat(@PathVariable Long id, @RequestBody Resultat resultat) {
        resultat.setId(id);
        // resultat.setLastModified(LocalDateTime.now()); // Update last modified timestamp if needed
        return resultatService.editResultat(id, resultat);
    }

    @ApiOperation(value = "Delete a resultat by ID")
    @DeleteMapping("/{id}")
    public void deleteResultat(@PathVariable Long id) {
        resultatService.deleteResultatById(id);
    }
}