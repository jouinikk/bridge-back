package com.example.cars.services;

import com.example.cars.Repositories.CompetitionRepository;
import com.example.cars.entities.Competition;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Competition edit(Long id, Competition updated) {
        Competition existing = competitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        existing.setNom(updated.getNom());
        existing.setDateDebut(updated.getDateDebut());
        existing.setDateFin(updated.getDateFin());
        existing.setLieu(updated.getLieu());
        existing.setDescription(updated.getDescription());
        existing.setUrlSource(updated.getUrlSource());
        existing.setEstActive(updated.isEstActive());
        existing.setLastModified(LocalDateTime.now());
        existing.setUpdatedNotificationSent(false);

        return competitionRepository.save(existing);
    }
}