package com.example.cars.services;

import com.example.cars.entities.Repas;
import java.util.List;

public interface IRepasService {
    Repas addRepas(Repas repas);
    List<Repas> getAllRepas();
    Repas getRepasById(Long id);
    void deleteRepas(Long id);
}