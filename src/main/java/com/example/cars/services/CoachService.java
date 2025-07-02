package com.example.cars.services;

import com.example.cars.entities.Coach;
import com.example.cars.Repositories.CoachRepository;
import com.example.cars.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService implements ICoachService {
    
    private final CoachRepository coachRepository;
    
    @Override
    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }
    
    @Override
    public Coach getCoachById(Long id) {
        return coachRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<Coach> getCoachesBySpecialite(String specialite) {
        return coachRepository.findBySpecialite(specialite);
    }
    
    @Override
    public Coach addCoach(Coach coach) {
        return coachRepository.save(coach);
    }
    
    @Override
    public Coach updateCoach(Coach coach) {
        return coachRepository.save(coach);
    }
    
    @Override
    public void deleteCoach(Long id) {
        coachRepository.deleteById(id);
    }
    
    @Override
    public List<Coach> getCoachWithGroupes(Long id) {
        Coach coach = coachRepository.findById(id).orElse(null);
        return coach != null ? Collections.singletonList(coach) : Collections.emptyList();
    }

    @Override
    public void addUserAsCoach(User user) {
        Coach coach = new Coach();
        coach.setId(Long.parseLong(String.valueOf(user.getId())));
        coach.setSpecialite(user.getSpecialite());
        coach.setNom(user.getName());
        coach.setPrenom(user.getPrenom());
        coach.setAnneeExperience(user.getAnneeExperience());
        coach.setEmail(user.getEmail());
        coach.setTelephone(user.getTelephone());

        coachRepository.save(coach);
    }
}