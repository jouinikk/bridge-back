package com.example.cars.dataImport;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")  // Added base path
public class DataImportController {

    private final DataImportService dataImportService;

    public DataImportController(DataImportService dataImportService) {
        this.dataImportService = dataImportService;
    }

    @PostMapping(value = "/import", consumes = {"multipart/form-data"})
    public ResponseEntity<?> importData(
            @RequestPart("file") MultipartFile file,
            @RequestParam("entity") String entity) {

        try {
            dataImportService.importDataFromJson(file, entity);
            return ResponseEntity.ok().body(Map.of(
                    "status", "success",
                    "message", "Imported " + entity + " data"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }
}