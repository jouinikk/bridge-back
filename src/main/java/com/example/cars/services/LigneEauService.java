package com.example.cars.services;

import com.example.cars.entities.LigneEau;
import com.example.cars.Repositories.LigneEauRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LigneEauService implements ILigneEauService {
    
    private final LigneEauRepository ligneEauRepository;
    
    @Override
    public LigneEau addLigneEau(LigneEau ligneEau) {
        return ligneEauRepository.save(ligneEau);
    }
    
    @Override
    public List<LigneEau> getAllLignesEau() {
        return ligneEauRepository.findAll();
    }
    
    @Override
    public LigneEau getLigneEauById(Long id) {
        return ligneEauRepository.findById(id).orElse(null);
    }
    
    @Override
    public LigneEau updateLigneEau(LigneEau ligneEau) {
        return ligneEauRepository.save(ligneEau);
    }
    
    @Override
    public void deleteLigneEau(Long id) {
        ligneEauRepository.deleteById(id);
    }
    
    @Override
    public List<LigneEau> getLignesEauByPiscineId(Long piscineId) {
        return ligneEauRepository.findByPiscineId(piscineId);
    }
    
    @Override
    public LigneEau getLigneEauByPiscineAndNumero(Long piscineId, String numero) {
        return ligneEauRepository.findByPiscineIdAndNumero(piscineId, numero);
    }
}