package com.example.cars.services;

import com.example.cars.entities.Nageur;

import java.util.List;

public interface INageurService {
    Nageur addNageur(Nageur nageur);
    List<Nageur> getAllNageurs();
    Nageur getNageurById(Long id);
    void deleteNageur(Long id);
}
