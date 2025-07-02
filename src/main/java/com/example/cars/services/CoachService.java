package com.example.cars.services;

import com.example.cars.entities.Coach;
import com.example.cars.entities.Seance;
import com.example.cars.Repositories.CoachRepository;
import com.example.cars.Repositories.SeanceRepository;
import com.example.cars.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachService implements ICoachService {
    
    private final CoachRepository coachRepository;
    private final SeanceRepository seanceRepository;
    
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
    public List<Coach> getAvailableCoaches(LocalDateTime dateDebut, LocalDateTime dateFin) {
        // Convert LocalDateTime to ZonedDateTime using Europe/Paris timezone to match database storage
        ZonedDateTime startDateTime = dateDebut.atZone(java.time.ZoneId.of("Europe/Paris"));
        ZonedDateTime endDateTime = dateFin.atZone(java.time.ZoneId.of("Europe/Paris"));
        
        // Get all coaches
        List<Coach> allCoaches = coachRepository.findAll();
        
        // Filter out coaches who have conflicting sessions
        return allCoaches.stream()
                .filter(coach -> isCoachAvailable(coach, startDateTime, endDateTime))
                .collect(Collectors.toList());
    }
    
    private boolean isCoachAvailable(Coach coach, ZonedDateTime dateDebut, ZonedDateTime dateFin) {
        // Check for conflicting sessions for this coach
        List<Seance> conflictingSeances = seanceRepository.findConflictingSessionsForCoach(
                coach.getId(), dateDebut, dateFin, null);
        
        // Coach is available only if no conflicting sessions
        return conflictingSeances.isEmpty();
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