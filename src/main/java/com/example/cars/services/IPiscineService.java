package com.example.cars.services;

import com.example.cars.entities.Piscine;

import java.util.List;

public interface IPiscineService {
    Piscine addPiscine(Piscine piscine);
    List<Piscine> getAllPiscines();
    Piscine getPiscineById(Long id);
    Piscine updatePiscine(Piscine piscine);
    void deletePiscine(Long id);
    List<Piscine> getPiscinesByVille(String ville);
    List<Piscine> searchPiscines(String keyword);
}