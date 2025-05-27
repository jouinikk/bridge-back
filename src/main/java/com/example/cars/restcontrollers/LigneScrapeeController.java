package com.example.cars.restcontrollers;

import com.example.cars.entities.LigneScrapee;
import com.example.cars.services.LigneScrapeeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Api(value ="Ligne Scrapee Controller", description = "")
@RestController
@RequestMapping("/LigneScrapee")
public class LigneScrapeeController {

    private final LigneScrapeeServiceImpl ligneScrapeeService;

    // Constructor injection
    public LigneScrapeeController(LigneScrapeeServiceImpl ligneScrapeeService) {
        this.ligneScrapeeService = ligneScrapeeService;
    }

    @ApiOperation(value = "Add a new scraped line", response = LigneScrapee.class)
    @PostMapping
    public LigneScrapee addLigneScrappee(@RequestBody LigneScrapee ligneScrapee) {
        return ligneScrapeeService.saveLigne(ligneScrapee);
    }

    @ApiOperation(value = "Get all scraped lines", response = List.class)
    @GetMapping
    public List<LigneScrapee> getAllLignes() {
        return ligneScrapeeService.getAll();
    }

    @ApiOperation(value = "Get a scraped line by Id", response = LigneScrapee.class)
    @GetMapping("/{id}")
    public Optional<LigneScrapee> getLigneById(@PathVariable Long id) {
        return ligneScrapeeService.getLigneById(id);
    }

    @ApiOperation(value = "Update an existing scraped line", response = LigneScrapee.class)
    @PutMapping("/{id}")
    public LigneScrapee updateLigne(@PathVariable Long id, @RequestBody LigneScrapee ligneScrapee) {
        ligneScrapee.setId(id);
        return ligneScrapeeService.editLigne(id, ligneScrapee);
    }

    @ApiOperation(value = "Delete a scraped line by ID")
    @DeleteMapping("/{id}")
    public void deleteLigne(@PathVariable Long id) {
        ligneScrapeeService.deleteLigneScrappe(id);
    }
}