package com.example.cars.services;

import com.example.cars.Repositories.HistoriqueImportRepository;
import com.example.cars.entities.HistoriqueImport;
import com.example.cars.restcontrollers.HistoriqueImportController;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistoriqueImportServiceImpl implements IHistoriqueImportService {


    private final HistoriqueImportRepository historiqueRepository;

    public HistoriqueImportServiceImpl(HistoriqueImportRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @Override
    public HistoriqueImport createHistoriqueImport(HistoriqueImport historiqueImport) {
        return historiqueRepository.save(historiqueImport);
    }

    @Override
    public HistoriqueImport logImport(HistoriqueImport historique) {
        historique.setDateImport(LocalDateTime.now());
        return historiqueRepository.save(historique);
    }

    @Override
    public List<HistoriqueImport> getAllImports() {
        return historiqueRepository.findAll();
    }


    @Override
    public Optional<HistoriqueImport> getHistoriqueImportById(Long id) {
        return historiqueRepository.findById(id);
    }

    @Override
    public List<HistoriqueImport> getImportsByType(String type) {
        return historiqueRepository.findByTypeImport(type);
    }

    @Override
    public HistoriqueImport edit(HistoriqueImport historiqueImport) {
        return historiqueRepository.save(historiqueImport);
    }

    @Override
    public void deleteHistoriqueImportById(Long id) {
            historiqueRepository.deleteById(id);
    }
}

