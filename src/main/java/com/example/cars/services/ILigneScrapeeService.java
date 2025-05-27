package com.example.cars.services;

import com.example.cars.entities.LigneScrapee;

import java.util.List;
import java.util.Optional;

public interface ILigneScrapeeService {
    LigneScrapee saveLigne(LigneScrapee ligne);
    List<LigneScrapee> getAll();
    Optional<LigneScrapee> getLigneById(Long id);
    LigneScrapee editLigne(Long id, LigneScrapee updatedLigneScrapee);
    void deleteLigneScrappe(Long id);
}