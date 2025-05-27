package com.example.cars.dataImport;

import com.example.cars.entities.Resultat; // Changed to Resultat
import com.example.cars.Repositories.ResultatRepository; // Changed to ResultatRepository
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class DataImportService {

    private final ResultatRepository resultatRepository;
    private final ObjectMapper objectMapper;

    public DataImportService(ResultatRepository resultatRepository, ObjectMapper objectMapper) {
        this.resultatRepository = resultatRepository;
        this.objectMapper = objectMapper;
    }

    public void importDataFromJson(String jsonFilePath) {
        try {
            // Load file from classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jsonFilePath);
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + jsonFilePath);
            }

            // Parse JSON
            List<Resultat> results = objectMapper.readValue(inputStream, new TypeReference<List<Resultat>>() {});

            // Save to database
            resultatRepository.saveAll(results);
            System.out.println("Imported " + results.size() + " records.");

        } catch (Exception e) {
            System.err.println("IMPORT FAILED: " + e.getMessage());
            throw new RuntimeException("Import failed", e); // Re-throw to see the error in the API response
        }
    }
}