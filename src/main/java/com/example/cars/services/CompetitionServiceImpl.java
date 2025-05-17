package com.example.cars.services;

import com.example.cars.Repositories.CompetitionRepository;
import com.example.cars.entities.Competition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionServiceImpl implements ICompetitionService {

    private CompetitionRepository competitionRepository;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    @Override
    public Competition createCompetition(Competition competition) {
        return competitionRepository.save(competition);
    }

    @Override
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    @Override
    public Optional<Competition> getCompetitionById(Long id) {
        return competitionRepository.findById(id);
    }

    @Override
    public List<Competition> getCompetitionsActives() {
        return competitionRepository.findByEstActiveTrue();
    }

    @Override
    public void deleteCompetition(Long id) {
        competitionRepository.deleteById(id);
    }

    @Override
    public Competition edit(Competition competition) {
        return competitionRepository.save(competition);
    }
}