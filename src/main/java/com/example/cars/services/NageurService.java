package com.example.cars.services;

import com.example.cars.entities.Nageur;
import com.example.cars.Repositories.NageurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NageurService implements INageurService {
    private final NageurRepository nageurRepository;

    @Override
    public Nageur addNageur(Nageur nageur) {
        return nageurRepository.save(nageur);
    }

    @Override
    public List<Nageur> getAllNageurs() {
        return nageurRepository.findAll();
    }

    @Override
    public Nageur getNageurById(Long id) {
        return nageurRepository.findById(id).orElse(null);
    }

    @Override
    public List<Nageur> getNageursByNiveau(String niveau) {
        return nageurRepository.findByNiveau(niveau);
    }

    @Override
    public List<Nageur> getNageursByGroupeId(Long groupeId) {
        return nageurRepository.findByGroupesId(groupeId);
    }

    @Override
    public Nageur updateNageur(Nageur nageur) {
        return nageurRepository.save(nageur);
    }

    @Override
    public void deleteNageur(Long id) {
        nageurRepository.deleteById(id);
    }
}