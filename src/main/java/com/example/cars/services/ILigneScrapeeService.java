package com.example.cars.services;

import com.example.cars.entities.LigneScrapee;

import java.util.List;

public interface ILigneScrapeeService {
    LigneScrapee saveLigne(LigneScrapee ligne);
    List<LigneScrapee> getAll();
    LigneScrapee getLigneById(Long id);
    LigneScrapee edit(LigneScrapee ligneScrapee);
    void deleteLigneScrappe(Long id);
}