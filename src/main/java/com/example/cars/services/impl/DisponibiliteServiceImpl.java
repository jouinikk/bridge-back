package com.example.cars.services.impl;

import com.example.cars.entities.Disponibilite;
import com.example.cars.Repositories.DisponibiliteRepository;
import com.example.cars.services.IDisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DisponibiliteServiceImpl implements IDisponibiliteService {

    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

    @Override
    public Disponibilite addDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }

    @Override
    public List<Disponibilite> getAllDisponibilites() {
        return disponibiliteRepository.findAll();
    }

    @Override
    public Disponibilite getDisponibiliteById(Long id) {
        return disponibiliteRepository.findById(id).orElse(null);
    }

    @Override
    public Disponibilite updateDisponibilite(Disponibilite disponibilite) {
        if (disponibiliteRepository.existsById(disponibilite.getId())) {
            return disponibiliteRepository.save(disponibilite);
        }
        return null;
    }

    @Override
    public void deleteDisponibilite(Long id) {
        disponibiliteRepository.deleteById(id);
    }

    @Override
    public List<Disponibilite> getDisponibilitesByPiscineId(Long piscineId) {
        return disponibiliteRepository.findByPiscineId(piscineId);
    }

    @Override
    public List<Disponibilite> getDisponibilitesByLigneEauId(Long ligneEauId) {
        return disponibiliteRepository.findByLigneEauId(ligneEauId);
    }

    @Override
    public List<Disponibilite> getDisponibilitesByJourSemaine(DayOfWeek jourSemaine) {
        return disponibiliteRepository.findByJourSemaine(jourSemaine);
    }

    @Override
    public List<Disponibilite> getDisponibilitesByPiscineAndJour(Long piscineId, DayOfWeek jourSemaine) {
        return disponibiliteRepository.findByPiscineIdAndJourSemaine(piscineId, jourSemaine);
    }

    @Override
    public List<Disponibilite> getDisponibilitesByLigneEauAndJour(Long ligneEauId, DayOfWeek jourSemaine) {
        return disponibiliteRepository.findByLigneEauIdAndJourSemaine(ligneEauId, jourSemaine);
    }

    @Override
    public boolean isLigneEauDisponible(Long ligneEauId, LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();
        
        List<Disponibilite> disponibilites = disponibiliteRepository
            .findByLigneEauIdAndJourSemaine(ligneEauId, dayOfWeek);
            
        return disponibilites.stream()
            .anyMatch(d -> d.isEstDisponible() && 
                         !time.isBefore(d.getHeureOuverture()) && 
                         !time.isAfter(d.getHeureFermeture()));
    }
}