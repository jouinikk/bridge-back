package com.example.cars.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cars.entities.Repas;
import com.example.cars.Repositories.RepasRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepasService implements IRepasService {
    private final RepasRepository repasRepo;

    public Repas addRepas(Repas repas) {
        return repasRepo.save(repas);
    }

    public List<Repas> getAllRepas() {
        return repasRepo.findAll();
    }

    public Repas getRepasById(Long id) {
        return repasRepo.findById(id).orElse(null);
    }

    public void deleteRepas(Long id) {
        repasRepo.deleteById(id);
    }
}
