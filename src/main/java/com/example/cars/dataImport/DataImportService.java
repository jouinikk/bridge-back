package com.example.cars.dataImport;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class DataImportService {
    private final Map<String, DataImporter> importers;

    public DataImportService(CompetitionImporter competitionImporter,
                             ResultatImporter resultatImporter) {
        this.importers = Map.of(
                "competition", competitionImporter,
                "resultat", resultatImporter
        );
    }

    public void importDataFromJson(MultipartFile file, String entityName) throws Exception {
        DataImporter importer = importers.get(entityName.toLowerCase());
        if (importer == null) {
            throw new IllegalArgumentException("Unsupported entity: " + entityName);
        }
        importer.importData(file);
    }
}