package com.example.cars.services;

import com.example.cars.entities.Nageur;
import java.util.List;

public interface INageurService {
    List<Nageur> getAllNageurs();
    Nageur getNageurById(Long id);
    List<Nageur> getNageursByNiveau(String niveau);
    List<Nageur> getNageursByGroupeId(Long groupeId);
    Nageur addNageur(Nageur nageur);
    Nageur updateNageur(Nageur nageur);
    void deleteNageur(Long id);
}