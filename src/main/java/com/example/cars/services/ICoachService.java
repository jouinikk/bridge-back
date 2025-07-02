package com.example.cars.services;

import com.example.cars.entities.Coach;
import java.time.LocalDateTime;
import java.util.List;

public interface ICoachService {
    List<Coach> getAllCoaches();
    Coach getCoachById(Long id);
    List<Coach> getCoachesBySpecialite(String specialite);
    Coach addCoach(Coach coach);
    Coach updateCoach(Coach coach);
    void deleteCoach(Long id);
    List<Coach> getCoachWithGroupes(Long id);
    List<Coach> getAvailableCoaches(LocalDateTime dateDebut, LocalDateTime dateFin);
}