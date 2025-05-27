package com.example.cars.dataImport;

import com.example.cars.dataImport.DataImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataImportController {

    private final DataImportService dataImportService;

    public DataImportController(DataImportService dataImportService) {
        this.dataImportService = dataImportService;
    }

    @PostMapping("/import-results")
    public ResponseEntity<String> importResults(@RequestParam String jsonFile) {
        dataImportService.importDataFromJson(jsonFile);
        return ResponseEntity.ok("Results import process initiated.");
    }
}