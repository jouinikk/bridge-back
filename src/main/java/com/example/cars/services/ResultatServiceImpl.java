package com.example.cars.services;

import com.example.cars.Repositories.CompetitionRepository;
import com.example.cars.Repositories.ResultatRepository;
import com.example.cars.entities.Resultat;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ResultatServiceImpl implements IResultatService {

    private final ResultatRepository resultatRepository;
    private final CompetitionRepository competitionRepository;

    public ResultatServiceImpl(ResultatRepository resultatRepository, CompetitionRepository competitionRepository) {
        this.resultatRepository = resultatRepository;
        this.competitionRepository = competitionRepository;
    }

    @Override
    public Resultat addResultat(Resultat resultat) {
        return resultatRepository.save(resultat);
    }

    @Override
    public List<Resultat> getAllResultat() {
        return resultatRepository.findAll();
    }

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

    @Override
    public Resultat getResultatById(Long id) {
        return resultatRepository.findById(id).get();
    }

    @Override
    public void deleteResultatById(Long id) {
            resultatRepository.deleteById(id);
    }

    @Override
    public Resultat editResultat(Resultat resultat) {
        return resultatRepository.save(resultat);
    }
}