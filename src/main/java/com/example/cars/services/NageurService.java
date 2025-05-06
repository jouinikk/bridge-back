package com.example.cars.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cars.entities.Nageur;
import com.example.cars.Repositories.NageurRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NageurService implements INageurService {
    private final NageurRepository nageurRepository;

    public Nageur addNageur(Nageur nageur) {
        return nageurRepository.save(nageur);
    }

    public List<Nageur> getAllNageurs() {
        return nageurRepository.findAll();
    }

    public Nageur getNageurById(Long id) {
        return nageurRepository.findById(id).orElse(null);
    }

    public void deleteNageur(Long id) {
        nageurRepository.deleteById(id);
    }
}
