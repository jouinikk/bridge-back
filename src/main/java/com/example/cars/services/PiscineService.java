package com.example.cars.services;

import com.example.cars.entities.Disponibilite;
import com.example.cars.entities.Piscine;
import com.example.cars.Repositories.LigneEauRepository;
import com.example.cars.Repositories.DisponibiliteRepository;
import com.example.cars.Repositories.PiscineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PiscineService implements IPiscineService {

    private final PiscineRepository piscineRepository;
    private final LigneEauRepository ligneEauRepository;
    private final DisponibiliteRepository disponibiliteRepository;

    @Override
    public Piscine addPiscine(Piscine piscine) {
        if (piscine.getLignesEau() != null) {
            Piscine savedPiscine = piscineRepository.save(piscine);
            piscine.getLignesEau().forEach(ligne -> {
                ligne.setPiscine(savedPiscine);
                ligneEauRepository.save(ligne);
            });
            return piscineRepository.findById(savedPiscine.getId()).orElse(savedPiscine);
        }
        return piscineRepository.save(piscine);
    }

    @Override
    public List<Piscine> getAllPiscines() {
        return piscineRepository.findAll();
    }

    @Override
    public Piscine getPiscineById(Long id) {
        return piscineRepository.findById(id).orElse(null);
    }

    @Override
    public Piscine updatePiscine(Piscine piscine) {
        return piscineRepository.save(piscine);
    }

    @Override
    public void deletePiscine(Long id) {
        piscineRepository.deleteById(id);
    }

    @Override
    public List<Piscine> getPiscinesByVille(String ville) {
        return piscineRepository.findByVille(ville);
    }

    @Override
    public List<Piscine> searchPiscines(String keyword) {
        return piscineRepository.findByNomContaining(keyword);
    }

    @Override
    public List<Piscine> getAvailablePiscines(LocalDateTime dateDebut, LocalDateTime dateFin) {
        DayOfWeek dayOfWeek = dateDebut.getDayOfWeek();
        LocalTime timeDebut = dateDebut.toLocalTime();
        LocalTime timeFin = dateFin.toLocalTime();

        List<Piscine> allPiscines = piscineRepository.findAll();

        return allPiscines.stream()
                .filter(piscine -> isPiscineAvailable(piscine, dayOfWeek, timeDebut, timeFin))
                .collect(Collectors.toList());
    }

    private boolean isPiscineAvailable(Piscine piscine, DayOfWeek dayOfWeek,
                                       LocalTime timeDebut, LocalTime timeFin) {
        List<Disponibilite> disponibilites = disponibiliteRepository
                .findByPiscineIdAndJourSemaine(piscine.getId(), dayOfWeek);

        if (disponibilites.isEmpty()) {
            return false;
        }

        return disponibilites.stream().anyMatch(disp ->
                disp.isEstDisponible() &&
                        disp.getHeureOuverture() != null &&
                        disp.getHeureFermeture() != null &&
                        !timeDebut.isBefore(disp.getHeureOuverture()) &&
                        !timeFin.isAfter(disp.getHeureFermeture())
        );
    }
}