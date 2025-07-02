package com.example.cars.restcontrollers;

import com.example.cars.entities.LigneScrapee;
import com.example.cars.entities.LigneScrapee;
import com.example.cars.services.LigneScrapeeServiceImpl;
import com.example.cars.services.LigneScrapeeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Api(value ="Ligne scrappee", description = "")
@RestController
@RequestMapping("/Source")
public class LigneScrapeeController {

    

    private LigneScrapeeServiceImpl ligneScrapeeServiceImpl;

    @ApiOperation(value = "Add a new source", response = LigneScrapee.class)
    @PostMapping
    public LigneScrapee addLigneScrappee(@RequestBody LigneScrapee ligneScrapee) {
        return ligneScrapeeServiceImpl.saveLigne(ligneScrapee);
    }

    @ApiOperation(value = "Get all source", response = List.class)
    @GetMapping
    public List<LigneScrapee> getAllLignes() {
        return ligneScrapeeServiceImpl.getAll();
    }

    @ApiOperation(value = "Get a source by Id", response = LigneScrapee.class)
    @GetMapping("/{id}")
    public LigneScrapee getLigneById(@PathVariable Long id) {
        return ligneScrapeeServiceImpl.getLigneById(id);
    }


    @ApiOperation(value = "Update an existing source", response = LigneScrapee.class)
    @PutMapping
    public LigneScrapee updateLigne(@RequestBody LigneScrapee ligneScrapee) {
        return ligneScrapeeServiceImpl.edit(ligneScrapee);
    }

    @ApiOperation(value = "Delete a source by ID")
    @DeleteMapping("/{id}")
    public void deleteLigne(@PathVariable Long id) {
        ligneScrapeeServiceImpl.deleteLigneScrappe(id);
    }
}
