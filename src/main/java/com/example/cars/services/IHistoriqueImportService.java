package com.example.cars.services;

import com.example.cars.entities.HistoriqueImport;
import com.example.cars.restcontrollers.HistoriqueImportController;

import java.util.List;
import java.util.Optional;

public interface IHistoriqueImportService {
    HistoriqueImport createHistoriqueImport(HistoriqueImport historiqueImport);
    HistoriqueImport logImport(HistoriqueImport historique);
    List<HistoriqueImport> getAllImports();
    Optional<HistoriqueImport> getHistoriqueImportById(Long id);
    List<HistoriqueImport> getImportsByType(String type);
    HistoriqueImport editHistoriqueImport(Long id, HistoriqueImport updatedHistoriqueImport);
    void deleteHistoriqueImportById(Long id);
}