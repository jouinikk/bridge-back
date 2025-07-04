package com.example.cars.services;

import com.example.cars.entities.Resultat;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Optional;

public interface IResultatService {
    Resultat addResultat(Resultat resultat);
    Optional<Resultat> getResultatById(Long id);
    List<Resultat> getAllResultat();
    List<Resultat> getResultatsByCompetition(Long competitionId);
    List<Resultat> getResultatsByNageur(String nomNageur);
    void deleteResultatById(Long id);
    Resultat editResultat(Long id, Resultat updatedResultat);
}
