package com.example.cars.services;

import com.example.cars.entities.LigneEau;

import java.time.LocalDateTime;
import java.util.List;

public interface ILigneEauService {
    LigneEau addLigneEau(LigneEau ligneEau);
    List<LigneEau> getAllLignesEau();
    LigneEau getLigneEauById(Long id);
    LigneEau updateLigneEau(LigneEau ligneEau);
    void deleteLigneEau(Long id);
    List<LigneEau> getLignesEauByPiscineId(Long piscineId);
    LigneEau getLigneEauByPiscineAndNumero(Long piscineId, String numero);
    List<LigneEau> getAvailableLignesEau(LocalDateTime dateDebut, LocalDateTime dateFin);
    List<LigneEau> getAvailableLignesEauByPiscine(Long piscineId, LocalDateTime dateDebut, LocalDateTime dateFin);
}