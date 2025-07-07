package com.example.cars.services;



import com.example.cars.entities.SeanceBienEtre;

import java.util.List;

    public interface ISeanceBienEtreService {
        SeanceBienEtre addSeance(SeanceBienEtre seance);
        List<SeanceBienEtre> getAllSeances();
        SeanceBienEtre getSeanceById(Long id);
        void deleteSeance(Long id);
    }

