package com.example.cars.services;

import com.example.cars.entities.Disponibilite;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;


public interface IDisponibiliteService {
    Disponibilite addDisponibilite(Disponibilite disponibilite);
    List<Disponibilite> getAllDisponibilites();
    Disponibilite getDisponibiliteById(Long id);
    Disponibilite updateDisponibilite(Disponibilite disponibilite);
    void deleteDisponibilite(Long id);
    
    List<Disponibilite> getDisponibilitesByPiscineId(Long piscineId);
    List<Disponibilite> getDisponibilitesByLigneEauId(Long ligneEauId);
    List<Disponibilite> getDisponibilitesByJourSemaine(DayOfWeek jourSemaine);
    List<Disponibilite> getDisponibilitesByPiscineAndJour(Long piscineId, DayOfWeek jourSemaine);
    List<Disponibilite> getDisponibilitesByLigneEauAndJour(Long ligneEauId, DayOfWeek jourSemaine);
    
    boolean isLigneEauDisponible(Long ligneEauId, LocalDateTime dateTime);
}