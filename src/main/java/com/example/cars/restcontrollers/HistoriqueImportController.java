package com.example.cars.restcontrollers;

import com.example.cars.entities.Competition;
import com.example.cars.entities.HistoriqueImport;
import com.example.cars.services.CompetitionServiceImpl;
import com.example.cars.services.HistoriqueImportServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(value ="Histo Controller", description = "")
@RestController
@RequestMapping("/Histo")
public class HistoriqueImportController {

    private HistoriqueImportServiceImpl historiqueImportServiceImpl;

    @ApiOperation(value = "Add a new historique", response = HistoriqueImport.class)
    @PostMapping
    public HistoriqueImport addHist(@RequestBody HistoriqueImport historiqueImport) {
        return historiqueImportServiceImpl.createHistoriqueImport(historiqueImport);
    }

    @ApiOperation(value = "Get all Historique", response = List.class)
    @GetMapping
    public List<HistoriqueImport> getAllHistorique() {
        return historiqueImportServiceImpl.getAllImports();
    }

    @ApiOperation(value = "Get a histo import by Id", response = HistoriqueImport.class)
    @GetMapping("/{id}")
    public Optional<HistoriqueImport> getHistoImpoById(@PathVariable long id) {
        return historiqueImportServiceImpl.getHistoriqueImportById(id);
    }


    @ApiOperation(value = "Update an existing Histo import", response = HistoriqueImport.class)
    @PutMapping
    public HistoriqueImport updateHistoImport(@RequestBody HistoriqueImport historiqueImport) {
        return historiqueImportServiceImpl.edit(historiqueImport);
    }

    @ApiOperation(value = "Delete a histo import by ID")
    @DeleteMapping("/{id}")
    public void deleteHistoImport(@PathVariable Long id) {
        historiqueImportServiceImpl.deleteHistoriqueImportById(id);
    }

}
