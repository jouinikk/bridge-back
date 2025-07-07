package com.example.cars.services;

import com.example.cars.entities.Competition;

import java.util.List;
import java.util.Optional;

public interface ICompetitionService {
    Competition createCompetition(Competition competition);
    List<Competition> getAllCompetitions();
    Optional<Competition> getCompetitionById(Long id);
    List<Competition> getCompetitionsActives();
    void deleteCompetition(Long id);
    Competition edit(Long id, Competition updated);
}