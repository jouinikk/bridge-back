package com.example.cars.services;

import com.example.cars.Repositories.LigneScrapeeRepository;
import com.example.cars.entities.LigneScrapee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public LigneScrapee getLigneById(Long id) {
        return ligneRepo.findById(id).get();
    }

    @Override
    public LigneScrapee edit(LigneScrapee ligneScrapee) {
        return ligneRepo.save(ligneScrapee);
    }

    @Override
    public void deleteLigneScrappe(Long id) {
        ligneRepo.deleteById(id);
    }
}