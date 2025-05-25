package com.example.cars.services;

import com.example.cars.entities.Centre;
import java.util.List;

public interface ICentreService {
    Centre addCentre(Centre centre);
    Centre updateCentre(Centre centre);
    void deleteCentre(Long id);
    Centre getCentre(Long id);
    List<Centre> getAllCentres();
}