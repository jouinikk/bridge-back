package com.example.cars.dataImport;

import com.example.cars.Repositories.ResultatRepository;
import com.example.cars.entities.Resultat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ResultatImporter implements DataImporter {
    private final ResultatRepository repository;
    private final ObjectMapper objectMapper;

    public ResultatImporter(ResultatRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void importData(MultipartFile file) throws Exception {
        List<Resultat> resultats = objectMapper.readValue(
                file.getInputStream(),
                new TypeReference<List<Resultat>>() {}
        );

        // Resultat-specific validations
        resultats.forEach(resultat -> {
            if (resultat.getNomNageur() == null || resultat.getNomNageur().isBlank()) {
                throw new IllegalArgumentException("nom_nageur is required for Resultat");
            }
        });

        repository.saveAll(resultats);
    }
}