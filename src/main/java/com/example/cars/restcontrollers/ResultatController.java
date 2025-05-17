package com.example.cars.restcontrollers;

import com.example.cars.entities.Resultat;
import com.example.cars.services.ResultatServiceImpl;
import com.example.cars.services.ResultatServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value ="Result Controller", description = "")
@RestController
@RequestMapping("/Result")
public class ResultatController {
    
    private ResultatServiceImpl resultatServiceImpl;

    @ApiOperation(value = "Add a new resultat", response = Resultat.class)
    @PostMapping
    public Resultat addResult(@RequestBody Resultat resultat) {
        return resultatServiceImpl.addResultat(resultat);
    }

    @ApiOperation(value = "Get all resultat", response = List.class)
    @GetMapping
    public List<Resultat> getAllResults() {
        return resultatServiceImpl.getAllResultat();
    }

    @ApiOperation(value = "Get a resultat by Id", response = Resultat.class)
    @GetMapping("/{id}")
    public Resultat getResultById(@PathVariable Long id) {
        return resultatServiceImpl.getResultatById(id);
    }


    @ApiOperation(value = "Update an existing resultat", response = Resultat.class)
    @PutMapping
    public Resultat updateResultat(@RequestBody Resultat resultat) {
        return resultatServiceImpl.editResultat(resultat);
    }

    @ApiOperation(value = "Delete a resultat by ID")
    @DeleteMapping("/{id}")
    public void deleteResultat(@PathVariable Long id) {
        resultatServiceImpl.deleteResultatById(id);
    }
}
