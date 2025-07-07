package com.example.cars.restcontrollers;

import com.example.cars.entities.HistoriqueImport;
import com.example.cars.services.HistoriqueImportServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Api(value ="Histo Controller", description = "")
@RestController
@RequestMapping("/Histo")
public class HistoriqueImportController {

    private final HistoriqueImportServiceImpl historiqueImportService;

    // Constructor injection
    public HistoriqueImportController(HistoriqueImportServiceImpl historiqueImportService) {
        this.historiqueImportService = historiqueImportService;
    }

    @ApiOperation(value = "Add a new historique import", response = HistoriqueImport.class)
    @PostMapping
    public HistoriqueImport addHist(@RequestBody HistoriqueImport historiqueImport) {
        historiqueImport.setDateImport(LocalDateTime.now());
        return historiqueImportService.createHistoriqueImport(historiqueImport);
    }

    @ApiOperation(value = "Get all Historique imports", response = List.class)
    @GetMapping
    public List<HistoriqueImport> getAllHistorique() {
        return historiqueImportService.getAllImports();
    }

    @ApiOperation(value = "Get a historique import by Id", response = HistoriqueImport.class)
    @GetMapping("/{id}")
    public Optional<HistoriqueImport> getHistoImpoById(@PathVariable Long id) {
        return historiqueImportService.getHistoriqueImportById(id);
    }


    @ApiOperation(value = "Update an existing Historique import", response = HistoriqueImport.class)
    @PutMapping("/{id}")
    public HistoriqueImport updateHistoImport(@PathVariable Long id, @RequestBody HistoriqueImport historiqueImport) {
        historiqueImport.setId(id);
        return historiqueImportService.editHistoriqueImport(id, historiqueImport);
    }

    @ApiOperation(value = "Delete a historique import by ID")
    @DeleteMapping("/{id}")
    public void deleteHistoImport(@PathVariable Long id) {
        historiqueImportService.deleteHistoriqueImportById(id);
    }
}