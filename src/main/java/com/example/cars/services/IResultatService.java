package com.example.cars.services;

import com.example.cars.entities.Resultat;

import java.util.List;

public interface IResultatService {
    Resultat addResultat(Resultat resultat);
    List<Resultat> getAllResultat();
    List<Resultat> getResultatsByCompetition(Long competitionId);
    List<Resultat> getResultatsByNageur(String nomNageur);
    Resultat getResultatById(Long id);
    void deleteResultatById(Long id);
    Resultat editResultat(Resultat resultat);
}
