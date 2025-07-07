package com.example.cars.dataImport;

import com.example.cars.Repositories.CompetitionRepository;
import com.example.cars.entities.Competition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class CompetitionImporter implements DataImporter {
    private final CompetitionRepository repository;
    private final ObjectMapper objectMapper;

    public CompetitionImporter(CompetitionRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void importData(MultipartFile file) throws Exception {
        List<Competition> competitions = objectMapper.readValue(
                file.getInputStream(),
                new TypeReference<List<Competition>>() {}
        );

        // Competition-specific transformations
        competitions.forEach(comp -> {
            if (comp.getDateDebut() == null) {
                comp.setDateDebut(LocalDate.now());
            }
            comp.setCreatedNotificationSent(false);
            comp.setUpdatedNotificationSent(false);
        });

        repository.saveAll(competitions);
    }
}