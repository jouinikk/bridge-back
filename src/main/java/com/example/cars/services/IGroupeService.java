package com.example.cars.services;

import com.example.cars.entities.Groupe;
import java.util.List;

public interface IGroupeService {
    List<Groupe> getAllGroupes();
    Groupe getGroupeById(Long id);
    List<Groupe> getGroupesByCoachId(Long coachId);
    Groupe addGroupe(Groupe groupe);
    Groupe updateGroupe(Groupe groupe);
    void deleteGroupe(Long id);
    void addNageurToGroupe(Long groupeId, Long nageurId);
    void removeNageurFromGroupe(Long groupeId, Long nageurId);
}