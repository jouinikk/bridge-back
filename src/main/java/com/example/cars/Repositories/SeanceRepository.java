package com.example.cars.Repositories;

import com.example.cars.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {
    List<Seance> findByCoachId(Long coachId);
    List<Seance> findByGroupeId(Long groupeId);
    List<Seance> findByLigneEauId(Long ligneEauId);
    
    // Find all sessions for a specific coach between two dates
    List<Seance> findByCoachIdAndDateDebutBetween(Long coachId, ZonedDateTime debut, ZonedDateTime fin);
    
    // Find all sessions for a specific swimming lane between two dates
    List<Seance> findByLigneEauIdAndDateDebutBetween(Long ligneEauId, ZonedDateTime debut, ZonedDateTime fin);
    
    // Check for conflicting sessions (overlapping time) for a specific swimming lane
    // Two sessions conflict if they have overlapping time periods (not just touching boundaries)
    @Query("SELECT s FROM Seance s WHERE s.ligneEau.id = :ligneEauId " +
           "AND (s.id != :excludeId OR :excludeId IS NULL) " +
           "AND (s.dateDebut < :dateFin AND s.dateFin > :dateDebut)")
    List<Seance> findConflictingSessionsForLigneEau(
            @Param("ligneEauId") Long ligneEauId,
            @Param("dateDebut") ZonedDateTime dateDebut,
            @Param("dateFin") ZonedDateTime dateFin,
            @Param("excludeId") Long excludeId);
    
    // Check for conflicting sessions for a specific coach
    // Two sessions conflict if they have overlapping time periods (not just touching boundaries)
    @Query("SELECT s FROM Seance s WHERE s.coach.id = :coachId " +
           "AND (s.id != :excludeId OR :excludeId IS NULL) " +
           "AND (s.dateDebut < :dateFin AND s.dateFin > :dateDebut)")
    List<Seance> findConflictingSessionsForCoach(
            @Param("coachId") Long coachId,
            @Param("dateDebut") ZonedDateTime dateDebut,
            @Param("dateFin") ZonedDateTime dateFin,
            @Param("excludeId") Long excludeId);
}