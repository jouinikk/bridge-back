package com.example.cars.services;

import com.example.cars.entities.SeanceBienEtre;
import com.example.cars.repositories.SeanceBienEtreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public  class SeanceBienEtreService implements ISeanceBienEtreService {

    private final SeanceBienEtreRepository seanceRepository;

    @Override
    public SeanceBienEtre addSeance(SeanceBienEtre seance) {
        log.info("Ajout d'une nouvelle séance: {}", seance.getType());
        return seanceRepository.save(seance);
    }

    @Override
    public List<SeanceBienEtre> getAllSeances() {
        log.info("Récupération de toutes les séances");
        List<SeanceBienEtre> seances = seanceRepository.findAll();
        log.info("Nombre de séances trouvées: {}", seances.size());
        return seances;
    }

    @Override
    public SeanceBienEtre getSeanceById(Long id) {
        log.info("Recherche de la séance avec l'ID: {}", id);
        Optional<SeanceBienEtre> seance = seanceRepository.findById(id);
        if (seance.isPresent()) {
            log.info("Séance trouvée: {}", seance.get().getType());
            return seance.get();
        } else {
            log.warn("Aucune séance trouvée avec l'ID: {}", id);
            return null;
        }
    }

    @Override
    public void deleteSeance(Long id) {
        log.info("Suppression de la séance avec l'ID: {}", id);
        if (seanceRepository.existsById(id)) {
            seanceRepository.deleteById(id);
            log.info("Séance supprimée avec succès");
        } else {
            log.warn("Tentative de suppression d'une séance inexistante avec l'ID: {}", id);
            throw new RuntimeException("Séance non trouvée avec l'ID: " + id);
        }
    }
}