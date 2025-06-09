package com.example.cars.services;

import com.example.cars.entities.Piscine;
import com.example.cars.Repositories.LigneEauRepository;
import com.example.cars.Repositories.PiscineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PiscineService implements IPiscineService {

    private final PiscineRepository piscineRepository;
    private final LigneEauRepository ligneEauRepository;


    // @Override
    // public Piscine addPiscine(Piscine piscine) {
    //     return piscineRepository.save(piscine);
    // }

    @Override
    public Piscine addPiscine(Piscine piscine) {
        if (piscine.getLignesEau() != null) {
            // Save the piscine first to get an ID
            Piscine savedPiscine = piscineRepository.save(piscine);

            // Then save all lignesEau with the proper relationship
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
}