package com.example.cars.Repositories;
import com.example.cars.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Competition findById(long id);
    Competition findByNom(String nom);
    List<Competition> findByEstActiveTrue();
    List<Competition> findByDateDebutAfter(LocalDate date);
    List<Competition> findByDateDebutBefore(LocalDate date);
    List<Competition> findByLieu(String lieu);
    List<Competition> findByNomLike(String nom);
    List<Competition> findByUrlSource(String urlSource);
    List<Competition> findByDateImportationBetween(LocalDateTime start, LocalDateTime end);
}