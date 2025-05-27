package com.example.cars.services;

import com.example.cars.Repositories.CompetitionRepository;
import com.example.cars.Repositories.ResultatRepository;
import com.example.cars.entities.Resultat;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ResultatServiceImpl implements IResultatService {

    private  ResultatRepository resultatRepository;
    private CompetitionRepository competitionRepository;
    public ResultatServiceImpl(ResultatRepository resultatRepository) {
        this.resultatRepository = resultatRepository;
    }

    @Override
    public Resultat addResultat(Resultat resultat) {
        // You might want to add validation or business logic before saving
        return resultatRepository.save(resultat);
    }

    @Override
    public List<Resultat> getAllResultat() {
        return resultatRepository.findAll();
    }

    @Override
    public Optional<Resultat> getResultatById(Long id) {
        return resultatRepository.findById(id);
    }

    @Override
    public void deleteResultatById(Long id) {
        resultatRepository.deleteById(id);
    }

    @Override
    public Resultat editResultat(Long id, Resultat updatedResultat) {
        return resultatRepository.findById(id)
                .map(existingResultat -> {
                    // Update the fields of the existingResultat with the values from updatedResultat
                    existingResultat.setTemps(updatedResultat.getTemps());
                    existingResultat.setNomNageur(updatedResultat.getNomNageur());
                 //   existingResultat.setClassement(updatedResultat.getClassement());
                    existingResultat.setPoints(updatedResultat.getPoints());
                    existingResultat.setCompetition(updatedResultat.getCompetition()); // Assuming you want to update the competition as well

                    // Save the updated entity
                    return resultatRepository.save(existingResultat);
                })
                .orElse(null); // Or throw an exception if you prefer to handle not found cases differently
    }

    // The following methods were in the original service but are not directly corresponding
    // to the standard CRUD operations we are aligning with the controller.
    // Depending on your requirements, you might keep them or handle them differently.

     @Override
     public List<Resultat> getResultatsByCompetition(Long competitionId) {
         return competitionRepository.findById(competitionId)
                 .map(resultatRepository::findByCompetition)
                 .orElse(Collections.emptyList());
     }

     @Override
     public List<Resultat> getResultatsByNageur(String nomNageur) {
         return resultatRepository.findByNomNageurContainingIgnoreCase(nomNageur);
     }
}