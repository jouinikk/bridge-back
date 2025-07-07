package com.example.cars.services;

import com.example.cars.Repositories.HistoriqueImportRepository;
import com.example.cars.entities.HistoriqueImport;
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
        historiqueImport.setDateImport(LocalDateTime.now()); // Set date on creation
        return historiqueRepository.save(historiqueImport);
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
    public void deleteHistoriqueImportById(Long id) {
        historiqueRepository.deleteById(id);
    }

    @Override
    public HistoriqueImport editHistoriqueImport(Long id, HistoriqueImport updatedHistoriqueImport) {
        return historiqueRepository.findById(id)
                .map(existingHistoriqueImport -> {
                    existingHistoriqueImport.setTypeImport(updatedHistoriqueImport.getTypeImport());
                    existingHistoriqueImport.setDetails(updatedHistoriqueImport.getDetails());
                    existingHistoriqueImport.setUrlSource(updatedHistoriqueImport.getUrlSource());
                    existingHistoriqueImport.setCompetition(updatedHistoriqueImport.getCompetition());
                    // You might want to update the dateImport as well, depending on your requirements
                    // existingHistoriqueImport.setDateImport(LocalDateTime.now());
                    return historiqueRepository.save(existingHistoriqueImport);
                })
                .orElse(null); // Or throw an exception
    }

    // The following methods were in the original service but are not directly corresponding
    // to the standard CRUD operations we are aligning with the controller.
    // Depending on your requirements, you might keep them or handle them differently.

     @Override
     public HistoriqueImport logImport(HistoriqueImport historique) {
         historique.setDateImport(LocalDateTime.now());
         return historiqueRepository.save(historique);
     }

     @Override
     public List<HistoriqueImport> getImportsByType(String type) {
         return historiqueRepository.findByTypeImport(type);
     }
}