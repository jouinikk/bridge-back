package com.example.cars.Repositories;

import com.example.cars.entities.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {
    List<Disponibilite> findByPiscineId(Long piscineId);
    List<Disponibilite> findByLigneEauId(Long ligneEauId);
    List<Disponibilite> findByJourSemaine(DayOfWeek jourSemaine);
    List<Disponibilite> findByPiscineIdAndJourSemaine(Long piscineId, DayOfWeek jourSemaine);
    List<Disponibilite> findByLigneEauIdAndJourSemaine(Long ligneEauId, DayOfWeek jourSemaine);
    List<Disponibilite> findByEstDisponible(boolean estDisponible);
}