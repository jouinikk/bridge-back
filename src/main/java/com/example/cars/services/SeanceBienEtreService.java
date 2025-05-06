package com.example.cars.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cars.entities.SeanceBienEtre;
import com.example.cars.Repositories.SeanceBienEtreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeanceBienEtreService implements ISeanceBienEtreService {
    private final SeanceBienEtreRepository seanceRepository;

    public SeanceBienEtre addSeance(SeanceBienEtre seance) {
        return seanceRepository.save(seance);
    }

    public List<SeanceBienEtre> getAllSeances() {
        return seanceRepository.findAll();
    }

    public SeanceBienEtre getSeanceById(Long id) {
        return seanceRepository.findById(id).orElse(null);
    }

    public void deleteSeance(Long id) {
        seanceRepository.deleteById(id);
    }
}

