package com.example.cars.services.impl;

import com.example.cars.entities.Centre;
import com.example.cars.Repositories.CentreRepository;
import com.example.cars.services.ICentreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CentreServiceImpl implements ICentreService {
    
    private final CentreRepository centreRepository;

    @Override
    public Centre addCentre(Centre centre) {
        return centreRepository.save(centre);
    }

    @Override
    public Centre updateCentre(Centre centre) {
        return centreRepository.save(centre);
    }

    @Override
    public void deleteCentre(Long id) {
        centreRepository.deleteById(id);
    }

    @Override
    public Centre getCentre(Long id) {
        return centreRepository.findById(id).orElse(null);
    }

    @Override
    public List<Centre> getAllCentres() {
        return centreRepository.findAll();
    }
}