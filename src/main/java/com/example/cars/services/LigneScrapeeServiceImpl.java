package com.example.cars.services;

import com.example.cars.Repositories.LigneScrapeeRepository;
import com.example.cars.entities.LigneScrapee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LigneScrapeeServiceImpl implements ILigneScrapeeService {

    private final LigneScrapeeRepository ligneRepo;

    public LigneScrapeeServiceImpl(LigneScrapeeRepository ligneRepo) {
        this.ligneRepo = ligneRepo;
    }

    @Override
    public LigneScrapee saveLigne(LigneScrapee ligne) {
        ligne.setDateScrape(LocalDateTime.now());
        return ligneRepo.save(ligne);
    }

    @Override
    public List<LigneScrapee> getAll() {
        return ligneRepo.findAll();
    }

    @Override
    public Optional<LigneScrapee> getLigneById(Long id) {
        return ligneRepo.findById(id);
    }

    @Override
    public void deleteLigneScrappe(Long id) {
        ligneRepo.deleteById(id);
    }

    @Override
    public LigneScrapee editLigne(Long id, LigneScrapee updatedLigneScrapee) {
        return ligneRepo.findById(id)
                .map(existingLigneScrapee -> {
                    existingLigneScrapee.setContenuHtml(updatedLigneScrapee.getContenuHtml());
                    existingLigneScrapee.setUrlSource(updatedLigneScrapee.getUrlSource());
                    // You might choose to update the dateScrape as well, depending on your needs
                    // existingLigneScrapee.setDateScrape(LocalDateTime.now());
                    return ligneRepo.save(existingLigneScrapee);
                })
                .orElse(null); // Or throw an exception
    }
}