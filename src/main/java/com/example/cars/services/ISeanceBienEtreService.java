package com.example.cars.services;

import com.example.cars.entities.SeanceBienEtre;

import java.time.LocalDateTime;
import java.util.List;

public interface ISeanceBienEtreService {

    // Méthodes CRUD de base
    SeanceBienEtre addSeance(SeanceBienEtre seance);

    List<SeanceBienEtre> getAllSeances();

    SeanceBienEtre getSeanceById(Long id);

    void deleteSeance(Long id);

    // Méthodes de recherche spécialisées

}